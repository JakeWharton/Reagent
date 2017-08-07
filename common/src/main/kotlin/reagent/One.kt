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

import reagent.internal.one.OneFlatMapMany
import reagent.internal.one.OneFlatMapMaybe
import reagent.internal.one.OneFlatMapOne
import reagent.internal.one.OneFlatMapTask

/** Emits a single item or errors. */
abstract class One<out I> : Maybe<I>() {
  interface Listener<in I> {
    fun onItem(item: I)
    fun onError(t: Throwable)
  }

  abstract fun subscribe(listener: Listener<I>)
  override fun subscribe(listener: Maybe.Listener<I>) = subscribe(ListenerFromMaybe(listener))
  override fun subscribe(listener: Many.Listener<I>) = subscribe(ListenerFromMany(listener))

  override fun <O> flatMapMany(func: (I) -> Many<O>): Many<O> = OneFlatMapMany(this, func)
  override fun <O> flatMapMaybe(func: (I) -> Maybe<O>): Maybe<O> = OneFlatMapMaybe(this, func)
  override fun <O> flatMapOne(func: (I) -> One<O>): One<O> = OneFlatMapOne(this, func)
  override fun flatMapTask(func: (I) -> Task): Task = OneFlatMapTask(this, func)

  companion object Factory {
    //@JvmStatic
    fun <I> just(item: I): One<I> = Just(item)
    //@JvmStatic
    fun <I> error(t: Throwable): One<I> = Error(t)
    //@JvmStatic
    fun <I> returning(func: () -> I): One<I> = FromLambda(func)
  }

  internal class Just<out I>(private val item: I) : One<I>() {
    override fun subscribe(listener: Listener<I>) = listener.onItem(item)
  }

  internal class Error<out I>(private val t: Throwable) : One<I>() {
    override fun subscribe(listener: Listener<I>) = listener.onError(t)
  }

  internal class FromLambda<out I>(private val func: () -> I) : One<I>() {
    override fun subscribe(listener: Listener<I>) {
      val value: I
      try {
        value = func.invoke()
      } catch (t: Throwable) {
        listener.onError(t)
        return
      }
      listener.onItem(value)
    }
  }

  class ListenerFromMaybe<in U>(private val delegate: Maybe.Listener<U>) : Listener<U> {
    override fun onItem(item: U) = delegate.onItem(item)
    override fun onError(t: Throwable) = delegate.onError(t)
  }

  internal class ListenerFromMany<in I>(private val delegate: Many.Listener<I>): Listener<I> {
    override fun onItem(item: I) = delegate.run {
      onNext(item)
      onComplete()
    }

    override fun onError(t: Throwable) = delegate.onError(t)
  }
}
