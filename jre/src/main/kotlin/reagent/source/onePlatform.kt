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
package reagent.source

import kotlinx.coroutines.experimental.delay
import reagent.One
import java.time.Duration
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS

fun <I> Callable<I>.asOne(): One<I> = OneFromCallable(this)

@Deprecated(
    "Use overload that accepts a TimeUnit.",
    ReplaceWith("timer(delayMillis, TimeUnit.MILLISECONDS)", "java.util.concurrent.TimeUnit")
)
actual fun timer(delayMillis: Int): One<Unit> = OneTimerInt(delayMillis)

fun timer(delay: Long, unit: TimeUnit): One<Unit> = OneTimer(delay, unit)

fun Duration.asTimer(): One<Unit> = OneTimer(toMillis(), MILLISECONDS)

internal class OneFromCallable<out I>(private val func: Callable<I>) : One<I>() {
  override suspend fun produce() = func.call()
}

internal class OneDeferredCallable<out I>(private val func: Callable<One<I>>): One<I>() {
  override suspend fun produce() = func.call().produce()
}

internal class OneTimer(private val delay: Long, private val unit: TimeUnit): One<Unit>() {
  override suspend fun produce() = delay(delay, unit)
}
