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

import reagent.Observable
import reagent.One
import kotlin.DeprecationLevel.ERROR
import kotlin.DeprecationLevel.WARNING

fun <R, I : R> Observable<I>.reduce(operation: (accumulator: R, item: I) -> R): One<R> = ObservableReduce(this, operation)

@Deprecated("Reduce has no effect on single-element stream types", level = WARNING)
fun <R, I : R> One<I>.reduce(operation: (accumulator: R, item: I) -> R): One<R> = this

internal class ObservableReduce<out R, out I : R>(
  private val upstream: Observable<I>,
  private val operation: (accumulator: R, item: I) -> R
) : One<R>() {
  @Suppress("UNCHECKED_CAST") // 'accumulator' is set to an R instance before cast occurs.
  override suspend fun produce(): R {
    var first = true
    var accumulator: Any? = null
    upstream.subscribe {
      if (first) {
        accumulator = it
        first = false
      } else {
        accumulator = operation(accumulator as R, it)
      }
    }
    if (first) {
      throw NoSuchElementException("Reduce requires a non-empty Observable")
    }
    return accumulator as R
  }
}
