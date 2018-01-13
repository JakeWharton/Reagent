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
@file:JvmName("Manys")
package reagent.source

import reagent.Emitter
import reagent.Many
import java.util.concurrent.Callable

@JvmName("fromCallable")
fun <I> Callable<I>.asMany(): Many<I> = OneFromCallable(this)

@JvmName("fromRunnable")
@Suppress("UNCHECKED_CAST") // Never emits.
fun <I> Runnable.asMany(): Many<I> = TaskFromRunnable(this)

internal class ManyDeferredCallable<out I>(private val func: Callable<Many<I>>): Many<I>() {
  override suspend fun subscribe(emitter: Emitter<I>) = func.call().subscribe(emitter)
}
