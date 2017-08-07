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

import reagent.internal.many.ManyFlatMapMany
import reagent.internal.many.ManyFlatMapMaybe
import reagent.internal.many.ManyFlatMapOne
import reagent.internal.many.ManyFlatMapTask

/** Emits 0 to infinite items and then signals complete or error. */
abstract class Many<out I> {
  interface Listener<in I> {
    fun onNext(item: I)
    fun onComplete()
    fun onError(t: Throwable)
  }

  abstract fun subscribe(listener: Listener<I>)

  open fun <O> flatMapMany(func: (I) -> Many<O>): Many<O> = ManyFlatMapMany(this, func)
  open fun <O> flatMapMaybe(func: (I) -> Maybe<O>): Many<O> = ManyFlatMapMaybe(this, func)
  open fun <O> flatMapOne(func: (I) -> One<O>): Many<O> = ManyFlatMapOne(this, func)
  open fun flatMapTask(func: (I) -> Task): Task = ManyFlatMapTask(this, func)

  companion object Factory {
    //@JvmStatic
    fun <I> just(item: I): Many<I> = One.Just(item)
    //@JvmStatic
    //@JvmName("fromArray")
    // TODO rename to 'just' once @JvmName works: https://youtrack.jetbrains.com/issue/KT-19507.
    fun <I> fromArray(vararg items: I): Many<I> = FromArray(items)
    //@JvmStatic
    fun <I> empty() : Many<I> = Task.Complete
    //@JvmStatic
    fun <I> error(t: Throwable): Many<I> = One.Error(t)
    //@JvmStatic
    fun <I> returning(func: () -> I): Many<I> = One.FromLambda(func)
    //@JvmStatic
    fun <I> running(func: () -> Unit): Many<I> = Task.FromLambda(func)
  }

  internal class FromArray<out I>(private val items: Array<out I>) : Many<I>() {
    override fun subscribe(listener: Listener<I>) {
      for (item in items) {
        listener.onNext(item)
      }
      listener.onComplete()
    }
  }
}
