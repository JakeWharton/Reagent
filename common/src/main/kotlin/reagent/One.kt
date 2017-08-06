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

/** Emits a single item or errors. */
abstract class One<out I> : Maybe<I>() {
  interface Subscriber<in I> {
    fun onSubscribe(disposable: Disposable)
    fun onItem(item: I)
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

  abstract fun subscribe(subscriber: Subscriber<I>)
  override fun subscribe(subscriber: Maybe.Subscriber<I>) = subscribe(SubscriberFromMaybe(subscriber))
  override fun subscribe(subscriber: Many.Subscriber<I>) = subscribe(SubscriberFromMany(subscriber))

  companion object Factory {
    //@JvmStatic
    fun <I> just(item: I): One<I> = Just(item)
    //@JvmStatic
    fun <I> error(t: Throwable): One<I> = Error(t)
    //@JvmStatic
    fun <I> returning(func: () -> I): One<I> = FromLambda(func)
    //@JvmStatic
    fun <I> defer(func: () -> One<I>): One<I> = Deferred(func)
  }

  internal class Just<out I>(private val item: I) : One<I>() {
    override fun subscribe(subscriber: Subscriber<I>) = subscriber.onItem(item)
  }

  internal class Error<out I>(private val t: Throwable) : One<I>() {
    override fun subscribe(subscriber: Subscriber<I>) = subscriber.onError(t)
  }

  internal class FromLambda<out I>(private val func: () -> I) : One<I>() {
    override fun subscribe(subscriber: Subscriber<I>) {
      val value: I
      try {
        value = func.invoke()
      } catch (t: Throwable) {
        subscriber.onError(t)
        return
      }
      subscriber.onItem(value)
    }
  }

  internal class Deferred<out I>(private val func: () -> One<I>) : One<I>() {
    override fun subscribe(subscriber: Subscriber<I>) = func().subscribe(subscriber)
  }

  internal class SubscriberFromMaybe<in U>(private val delegate: Maybe.Subscriber<U>) : Subscriber<U> {
    override fun onSubscribe(disposable: Disposable) = delegate.onSubscribe(disposable)
    override fun onItem(item: U) = delegate.onItem(item)
    override fun onError(t: Throwable) = delegate.onError(t)
  }

  internal class SubscriberFromMany<in I>(private val delegate: Many.Subscriber<I>): Subscriber<I> {
    override fun onSubscribe(disposable: Disposable) = delegate.onSubscribe(disposable)
    override fun onItem(item: I) = delegate.run {
      onNext(item)
      onComplete()
    }
    override fun onError(t: Throwable) = delegate.onError(t)
  }
}
