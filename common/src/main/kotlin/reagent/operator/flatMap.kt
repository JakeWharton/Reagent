/*
 * Copyright 2017 Jake Wharton
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
package reagent.operator

import reagent.Disposable
import reagent.Many
import reagent.Maybe
import reagent.One
import reagent.Task
import kotlin.DeprecationLevel.ERROR

fun <I, O> Many<I>.flatMap(func: (I) -> Many<O>): Many<O> = ManyFlatMapMany(this, func)

internal class ManyFlatMapMany<U, out D>(
    private val upstream: Many<U>,
    private val func: (U) -> Many<D>
) : Many<D>() {
  override fun subscribe(subscriber: Subscriber<D>) = TODO()
}

fun <I> Many<I>.flatMap(func: (I) -> Task): Task = ManyFlatMapTask(this, func)

internal class ManyFlatMapTask<U>(
    private val upstream: Many<U>,
    private val func: (U) -> Task
) : Task() {
  override fun subscribe(subscriber: Subscriber) = TODO()
}

fun <I, O> Maybe<I>.flatMap(func: (I) -> Maybe<O>): Maybe<O> = MaybeFlatMapMaybe(this, func)

internal class MaybeFlatMapMaybe<U, out D>(
    private val upstream: Maybe<U>,
    private val func: (U) -> Maybe<D>
) : Maybe<D>() {
  override fun subscribe(subscriber: Subscriber<D>) = upstream.subscribe(Operator(
      subscriber, func))

  class Operator<in U, D>(
      private val downstream: Subscriber<D>,
      private val func: (U) -> Maybe<D>
  ) : Subscriber<U> {
    override fun onSubscribe(disposable: Disposable) = downstream.onSubscribe(disposable)
    override fun onItem(item: U) = func.invoke(item).subscribe(downstream)
    override fun onNothing() = downstream.onNothing()
    override fun onError(t: Throwable) = downstream.onError(t)
  }
}

fun <I, O> One<I>.flatMap(func: (I) -> One<O>): One<O> = OneFlatMapOne(this, func)

internal class OneFlatMapOne<U, D>(
    private val upstream: One<U>,
    private val func: (U) -> One<D>
) : One<D>() {
  override fun subscribe(subscriber: Subscriber<D>) = upstream.subscribe(Operator(subscriber, func))

  class Operator<in U, D>(
      private val downstream: Subscriber<D>,
      private val func: (U) -> One<D>
  ) : Subscriber<U> {
    override fun onSubscribe(disposable: Disposable) = downstream.onSubscribe(disposable)
    override fun onItem(item: U) = func.invoke(item).subscribe(downstream)
    override fun onError(t: Throwable) = downstream.onError(t)
  }
}

@Deprecated("Task has no items so mapping does not make sense.", level = ERROR)
fun Task.flatMap(func: (Nothing) -> Many<*>): Task = this
