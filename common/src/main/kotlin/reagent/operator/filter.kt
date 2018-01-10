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

import reagent.Many
import reagent.Maybe
import reagent.One
import reagent.Task
import kotlin.DeprecationLevel.ERROR

fun <I> Many<I>.filter(predicate: (I) -> Boolean): Many<I> = ManyFilter(this, predicate)

fun <I> Maybe<I>.filter(predicate: (I) -> Boolean): Maybe<I> = MaybeFilter(this, predicate)

fun <I> One<I>.filter(predicate: (I) -> Boolean): Maybe<I> = OneFilter(this, predicate)

@Suppress("DeprecatedCallableAddReplaceWith") // TODO https://youtrack.jetbrains.com/issue/KT-19512
@Deprecated("Task has no items so filtering does not make sense.", level = ERROR)
fun Task.filter(predicate: (Nothing) -> Boolean) = this

internal class ManyFilter<out I>(
    private val upstream: Many<I>,
    private val predicate: (I) -> Boolean
) : Many<I>() {
  override suspend fun subscribe(observer: Observer<I>) {
    upstream.subscribe(Operator(observer, predicate))
  }

  class Operator<in I>(
      private val downstream: Observer<I>,
      private val predicate: (I) -> Boolean
  ) : Many.Observer<I> by downstream {
    override suspend fun onNext(item: I) {
      if (predicate(item)) {
        downstream.onNext(item)
      }
    }
  }
}

internal class MaybeFilter<out I>(
    private val upstream: Maybe<I>,
    private val predicate: (I) -> Boolean
) : Maybe<I>() {
  override suspend fun subscribe(observer: Observer<I>) {
    upstream.subscribe(Operator(observer, predicate))
  }

  class Operator<in I>(
      private val downstream: Observer<I>,
      private val predicate: (I) -> Boolean
  ) : Observer<I> by downstream {
    override suspend fun onItem(item: I) {
      if (predicate(item)) {
        downstream.onItem(item)
      } else {
        downstream.onNothing()
      }
    }
  }
}

internal class OneFilter<out I>(
    private val upstream: One<I>,
    private val predicate: (I) -> Boolean
) : Maybe<I>() {
  override suspend fun subscribe(observer: Observer<I>) {
    upstream.subscribe(Operator(observer, predicate))
  }

  class Operator<in I>(
      private val downstream: Observer<I>,
      private val predicate: (I) -> Boolean
  ) : One.Observer<I> {
    override suspend fun onItem(item: I) {
      if (predicate(item)) {
        downstream.onItem(item)
      } else {
        downstream.onNothing()
      }
    }

    override suspend fun onError(t: Throwable) = downstream.onError(t)
  }
}
