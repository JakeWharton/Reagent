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
import reagent.internal.maybe.MaybeFlatMapMany
import reagent.internal.maybe.MaybeFlatMapMaybe
import reagent.internal.maybe.MaybeFlatMapOne
import reagent.internal.maybe.MaybeFlatMapTask

/** Emits an item, signals nothing (no item), or signals error. */
abstract class Maybe<out I> : Many<I>() {
  interface Subscriber<in I> {
    fun onSubscribe(disposable: Disposable)
    fun onItem(item: I)
    fun onNothing()
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
  override fun subscribe(subscriber: Many.Subscriber<I>) = subscribe(SubscriberFromMany(subscriber))

  override fun <O> flatMapMany(func: (I) -> Many<O>): Many<O> = MaybeFlatMapMany(this, func)
  override fun <O> flatMapMaybe(func: (I) -> Maybe<O>): Maybe<O> = MaybeFlatMapMaybe(this, func)
  override fun <O> flatMapOne(func: (I) -> One<O>): Maybe<O> = MaybeFlatMapOne(this, func)
  override fun flatMapTask(func: (I) -> Task): Task = MaybeFlatMapTask(this, func)

  companion object Factory {
    //@JvmStatic
    fun <I> just(item: I) : Maybe<I> = One.Just(item)
    //@JvmStatic
    fun <I> empty() : Maybe<I> = Task.Complete
    //@JvmStatic
    fun <I> error(t: Throwable): Maybe<I> = Task.Error(t)
    //@JvmStatic
    fun <I> returning(func: () -> I): Maybe<I> = One.FromLambda(func)
    //@JvmStatic
    fun <I> running(func: () -> Unit): Maybe<I> = Task.FromLambda(func)
    //@JvmStatic
    fun <I> defer(func: () -> Maybe<I>): Maybe<I> = Deferred(func)
  }

  internal class Deferred<out I>(private val func: () -> Maybe<I>): Maybe<I>() {
    override fun subscribe(subscriber: Subscriber<I>) = func().subscribe(subscriber)
  }

  internal class SubscriberFromMany<in U>(private val delegate: Many.Subscriber<U>) : Subscriber<U> {
    override fun onSubscribe(disposable: Disposable) = delegate.onSubscribe(disposable)
    override fun onItem(item: U) = delegate.run {
      onNext(item)
      onComplete()
    }
    override fun onNothing() = delegate.onComplete()
    override fun onError(t: Throwable) = delegate.onError(t)
  }
}
