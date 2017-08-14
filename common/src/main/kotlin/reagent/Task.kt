/*
 * Copyright 2016 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package reagent

import reagent.internal.AtomicRef
import kotlin.DeprecationLevel.HIDDEN

/** Signals complete or error. Has no items. */
abstract class Task : Maybe<Nothing>() {
  interface Subscriber {
    fun onSubscribe(disposable: Disposable)
    fun onComplete()
    fun onError(t: Throwable)
  }

  abstract class Observer<in I> : reagent.Many.Subscriber<I>, Disposable {
    private val ref = AtomicRef<Disposable?>(null)

    override final fun onSubscribe(disposable: Disposable) {
      ref.setOnceThen(disposable, this::onStart)
    }

    open fun onStart() = Unit

    override final fun dispose() {
      ref.dispose()
    }

    override final val isDisposed get() = ref.isDisposed
  }

  abstract fun subscribe(subscriber: Subscriber)
  override fun subscribe(subscriber: Maybe.Subscriber<Nothing>) = subscribe(ListenerFromMaybe(subscriber))
  override fun subscribe(subscriber: Many.Subscriber<Nothing>) = subscribe(ListenerFromMany(subscriber))

  @Deprecated("Task has no items so mapping does not make sense.", level = HIDDEN)
  override fun <O> flatMapMany(func: (Nothing) -> Many<O>): Many<O> = this
  @Deprecated("Task has no items so mapping does not make sense.", level = HIDDEN)
  override fun <O> flatMapMaybe(func: (Nothing) -> Maybe<O>): Maybe<O> = this
  @Deprecated("Task has no items so mapping does not make sense.", level = HIDDEN)
  override fun <O> flatMapOne(func: (Nothing) -> One<O>): Maybe<O> = this
  @Deprecated("Task has no items so mapping does not make sense.", level = HIDDEN)
  override fun flatMapTask(func: (Nothing) -> Task): Task = this

  companion object Factory {
    //@JvmStatic
    fun empty(): Task = Complete
    //@JvmStatic
    fun error(t: Throwable): Task = Error(t)
    //@JvmStatic
    fun running(func: () -> Unit): Task = FromLambda(func)
    //@JvmStatic
    fun defer(func: () -> Task): Task = Deferred(func)
  }

  internal object Complete : Task() {
    override fun subscribe(subscriber: Subscriber) = subscriber.onComplete()
  }

  internal class Error(private val t: Throwable) : Task() {
    override fun subscribe(subscriber: Subscriber) = subscriber.onError(t)
  }

  internal class FromLambda(private val func: () -> Unit) : Task() {
    override fun subscribe(subscriber: Subscriber) {
      try {
        func.invoke()
      } catch (t: Throwable) {
        subscriber.onError(t)
        return
      }
      subscriber.onComplete()
    }
  }

  internal class Deferred(private val func: () -> Task): Task() {
    override fun subscribe(subscriber: Subscriber) = func().subscribe(subscriber)
  }

  internal class ListenerFromMaybe(private val delegate: Maybe.Subscriber<Nothing>) : Subscriber {
    override fun onSubscribe(disposable: Disposable) = delegate.onSubscribe(disposable)
    override fun onComplete() = delegate.onNothing()
    override fun onError(t: Throwable) = delegate.onError(t)
  }

  internal class ListenerFromMany(private val delegate: Many.Subscriber<Nothing>): Subscriber {
    override fun onSubscribe(disposable: Disposable) = delegate.onSubscribe(disposable)
    override fun onComplete() = delegate.onComplete()
    override fun onError(t: Throwable) = delegate.onError(t)
  }
}
