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

import reagent.Emitter
import reagent.Observable
import reagent.Task

fun <I> Observable<I>.filter(predicate: (I) -> Boolean): Observable<I> = ObservableFilter(this, predicate)

fun <I : Any> Task<I>.filter(predicate: (I) -> Boolean): Task<I?> = TaskFilter(this, predicate)

internal class ObservableFilter<out I>(
    private val upstream: Observable<I>,
    private val predicate: (I) -> Boolean
) : Observable<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    upstream.subscribe {
      if (predicate(it)) {
        emit(it)
      } else {
        true
      }
    }
  }
}

internal class TaskFilter<out I : Any>(
    private val upstream: Task<I>,
    private val predicate: (I) -> Boolean
) : Task<I?>() {
  override suspend fun produce(): I? {
    val value = upstream.produce()
    return if (predicate(value)) value else null
  }
}
