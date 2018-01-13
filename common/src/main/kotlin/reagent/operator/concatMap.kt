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
import reagent.Many
import reagent.Task
import kotlin.DeprecationLevel.ERROR

fun <I, O> Many<I>.concatMap(func: (I) -> Many<O>): Many<O> = ManyConcatMapMany(this, func)

internal class ManyConcatMapMany<U, out D>(
  private val upstream: Many<U>,
  private val func: (U) -> Many<D>
) : Many<D>() {
  override suspend fun subscribe(emit: Emitter<D>) {
    upstream.subscribe {
      func(it).subscribe(emit)
    }
  }
}

fun <I> Many<I>.concatMap(func: (I) -> Task): Task = ManyConcatMapTask(this, func)

internal class ManyConcatMapTask<I>(
  private val upstream: Many<I>,
  private val func: (I) -> Task
) : Task() {
  override suspend fun run() {
    upstream.subscribe {
      func(it).run()
    }
  }
}

@Deprecated("Task produces no items so mapping has no effect.", level = ERROR)
fun Task.concatMap(func: (Nothing) -> Many<*>): Task = this
