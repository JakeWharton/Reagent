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

fun <I, O> Observable<I>.flatMap(func: (I) -> Observable<O>): Observable<O> = ObservableFlatMapObservable(this, func)

internal class ObservableFlatMapObservable<U, out D>(
    private val upstream: Observable<U>,
    private val func: (U) -> Observable<D>
) : Observable<D>() {
  override suspend fun subscribe(emit: Emitter<D>) = TODO()
}

fun <I, O> Task<I>.flatMap(func: (I) -> Task<O>): Task<O> = TaskFlatMapTask(this, func)

internal class TaskFlatMapTask<U, D>(
    private val upstream: Task<U>,
    private val func: (U) -> Task<D>
) : Task<D>() {
  override suspend fun produce() = func(upstream.produce()).produce()
}
