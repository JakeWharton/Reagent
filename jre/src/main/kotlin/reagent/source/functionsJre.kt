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

import kotlinx.coroutines.experimental.channels.ReceiveChannel
import reagent.Many
import reagent.Maybe
import reagent.One
import reagent.Task
import java.time.Duration
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS

fun <I> Callable<I>.asMany(): Many<I> = OneFromCallable(this)
fun <I> Callable<I>.asMaybe(): Maybe<I> = OneFromCallable(this)
fun <I> Callable<I>.asOne(): One<I> = OneFromCallable(this)
fun Callable<*>.asTask(): Task = TaskFromCallable(this)

fun <I> Runnable.asMany(): Many<I> = TaskFromRunnable(this)
fun <I> Runnable.asMaybe(): Maybe<I> = TaskFromRunnable(this)
fun Runnable.asTask(): Task = TaskFromRunnable(this)

fun <I> ReceiveChannel<I>.toMany(): Many<I> = ManyFromChannel(this)

@Deprecated(
    "Use overload that accepts a TimeUnit.",
    ReplaceWith("interval(periodMillis, TimeUnit.MILLISECONDS)", "java.util.concurrent.TimeUnit")
)
actual fun interval(periodMillis: Int): Many<Int> = ManyIntervalInt(periodMillis)
fun interval(period: Long, unit: TimeUnit): Many<Int> = ManyInterval(period, unit)
fun Duration.asInterval(): Many<Int> = ManyInterval(toMillis(), MILLISECONDS)

@Deprecated(
    "Use overload that accepts a TimeUnit.",
    ReplaceWith("timer(delayMillis, TimeUnit.MILLISECONDS)", "java.util.concurrent.TimeUnit")
)
actual fun timer(delayMillis: Int): One<Unit> = OneTimerInt(delayMillis)
fun timer(delay: Long, unit: TimeUnit): One<Unit> = OneTimer(delay, unit)
fun Duration.asTimer(): One<Unit> = OneTimer(toMillis(), MILLISECONDS)
