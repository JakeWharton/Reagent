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
import reagent.Task

fun <I, R> Observable<I>.fold(initial: R, operation: (accumulator: R, item: I) -> R): Task<R> = ObservableFold(this, initial, operation)

internal class ObservableFold<out I, R>(
  private val upstream: Observable<I>,
  private val initial: R,
  private val operation: (accumulator: R, item: I) -> R
) : Task<R>() {
  override suspend fun produce(): R {
    var value = initial
    upstream.subscribe {
      value = operation(value, it)
      return@subscribe true
    }
    return value
  }
}
