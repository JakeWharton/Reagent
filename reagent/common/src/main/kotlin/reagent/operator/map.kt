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

fun <I, O> Observable<I>.map(mapper: (I) -> O): Observable<O> = ObservableMap(this, mapper)

fun <I, O> Task<I>.map(mapper: (I) -> O): Task<O> = TaskMap(this, mapper)

internal class ObservableMap<in U, out D>(
    private val upstream: Observable<U>,
    private val mapper: (U) -> D
) : Observable<D>() {
  override suspend fun subscribe(emit: Emitter<D>) {
    upstream.subscribe { emit(mapper(it)) }
  }
}

internal class TaskMap<in U, out D>(
    private val upstream: Task<U>,
    private val mapper: (U) -> D
) : Task<D>() {
  override suspend fun produce() = mapper(upstream.produce())
}
