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

import reagent.internal.many.ManyFromOne
import reagent.internal.maybe.MaybeFromOne
import reagent.internal.one.OneError
import reagent.internal.one.OneFlatMapMany
import reagent.internal.one.OneFlatMapMaybe
import reagent.internal.one.OneFlatMapOne
import reagent.internal.one.OneFlatMapTask
import reagent.internal.one.OneFromCallable
import reagent.internal.one.OneFromLambda
import reagent.internal.one.OneJust
import reagent.internal.one.OneMap
import java.util.concurrent.Callable

/** Emits a single item or errors. */
abstract class One<I> : Maybe<I>() {
  interface Listener<in I> {
    fun onItem(item: I)
    fun onError(t: Throwable)
  }

  abstract fun subscribe(listener: Listener<I>)
  override final fun subscribe(listener: Maybe.Listener<I>) = toMaybe().subscribe(listener)

  /** Hide this `One` instance as a `Maybe`. */
  open fun toMaybe(): Maybe<I> = MaybeFromOne(this)
  /** Hide this `One` instance as a `Many`. */
  override fun toMany(): Many<I> = ManyFromOne(this)

  override fun <O> map(func: (I) -> O): One<O> = OneMap(this, func)

  override fun <O> flatMapMany(func: (I) -> Many<O>): Many<O> = OneFlatMapMany(this, func)
  override fun <O> flatMapMaybe(func: (I) -> Maybe<O>): Maybe<O> = OneFlatMapMaybe(this, func)
  override fun <O> flatMapOne(func: (I) -> One<O>): One<O> = OneFlatMapOne(this, func)
  override fun flatMapTask(func: (I) -> Task): Task = OneFlatMapTask(this, func)

  companion object Factory {
    @JvmStatic
    fun <I> just(item: I): One<I> = OneJust(item)
    @JvmStatic
    fun <I> error(t: Throwable): One<I> = OneError(t)
    @JvmStatic
    fun <I> returning(func: () -> I): One<I> = OneFromLambda(func)
    @JvmStatic
    fun <I> fromCallable(func: Callable<I>): One<I> = OneFromCallable(func)
  }
}
