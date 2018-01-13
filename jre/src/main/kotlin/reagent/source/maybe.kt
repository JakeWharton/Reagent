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
@file:JvmName("Maybes")
package reagent.source

import reagent.Maybe
import java.util.concurrent.Callable

@JvmName("fromCallable")
fun <I> Callable<I>.asMaybe(): Maybe<I> = OneFromCallable(this)

@JvmName("fromRunnable")
@Suppress("UNCHECKED_CAST") // Never emits.
fun <I> Runnable.asMaybe(): Maybe<I> = TaskFromRunnable(this)

internal class MaybeDeferredCallable<out I>(private val func: Callable<Maybe<I>>): Maybe<I>() {
  override suspend fun produce() = func.call().produce()
}
