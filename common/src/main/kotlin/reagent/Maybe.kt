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

import reagent.internal.many.ManyFromMaybe
import reagent.internal.maybe.MaybeFlatMapMany
import reagent.internal.maybe.MaybeFlatMapMaybe
import reagent.internal.maybe.MaybeFlatMapOne
import reagent.internal.maybe.MaybeFlatMapTask
import reagent.internal.maybe.MaybeMap
import reagent.internal.one.OneFromLambda
import reagent.internal.one.OneJust
import reagent.internal.task.TaskComplete
import reagent.internal.task.TaskError
import reagent.internal.task.TaskFromLambda

/** Emits an item, signals nothing (no item), or signals error. */
abstract class Maybe<I> : Many<I>() {
  interface Listener<in I> {
    fun onItem(item: I)
    fun onNothing()
    fun onError(t: Throwable)
  }

  abstract fun subscribe(listener: Listener<I>)
  override final fun subscribe(listener: Many.Listener<I>) = toMany().subscribe(listener)

  /** Hide this `Maybe` instance as a `Many`. */
  open fun toMany() : Many<I> = ManyFromMaybe(this)

  override fun <O> map(func: (I) -> O): Maybe<O> = MaybeMap(this, func)

  override fun <O> flatMapMany(func: (I) -> Many<O>): Many<O> = MaybeFlatMapMany(this, func)
  override fun <O> flatMapMaybe(func: (I) -> Maybe<O>): Maybe<O> = MaybeFlatMapMaybe(this, func)
  override fun <O> flatMapOne(func: (I) -> One<O>): Maybe<O> = MaybeFlatMapOne(this, func)
  override fun flatMapTask(func: (I) -> Task): Task = MaybeFlatMapTask(this, func)

  companion object Factory {
    //@JvmStatic
    fun <I> just(item: I) : Maybe<I> = OneJust(item)
    //@JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <I> empty() : Maybe<I> = TaskComplete as Maybe<I>
    //@JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <I> error(t: Throwable): Maybe<I> = TaskError(t) as Maybe<I>
    //@JvmStatic
    fun <I> returning(func: () -> I): Maybe<I> = OneFromLambda(func)
    //@JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <I> running(func: () -> Unit): Maybe<I> = TaskFromLambda(func) as Maybe<I>
  }
}
