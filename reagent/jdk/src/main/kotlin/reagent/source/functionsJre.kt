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
import reagent.Observable
import reagent.Maybe
import reagent.One
import reagent.Task
import java.time.Duration
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS

fun <I> Callable<I>.asMany(): Observable<I> = OneFromCallable(this)
fun <I> Callable<I>.asMaybe(): Maybe<I> = OneFromCallable(this)
fun <I> Callable<I>.asOne(): One<I> = OneFromCallable(this)
fun Callable<*>.asTask(): Task = TaskFromCallable(this)

fun <I> Runnable.asMany(): Observable<I> = TaskFromRunnable(this)
fun <I> Runnable.asMaybe(): Maybe<I> = TaskFromRunnable(this)
fun Runnable.asTask(): Task = TaskFromRunnable(this)

fun <I> ReceiveChannel<I>.toMany(): Observable<I> = ObservableFromChannel(this)

@Deprecated(
    "Use overload that accepts a TimeUnit.",
    ReplaceWith("interval(periodMillis, MILLISECONDS)", "java.util.concurrent.TimeUnit.MILLISECONDS")
)
actual fun interval(periodMillis: Int): Observable<Int> = ObservableIntervalInt(periodMillis)
fun interval(period: Long, unit: TimeUnit): Observable<Int> = ObservableInterval(period, unit)
fun Duration.asInterval(): Observable<Int> = ObservableInterval(toMillis(), MILLISECONDS)

@Deprecated(
    "Use overload that accepts a TimeUnit.",
    ReplaceWith("timer(delayMillis, MILLISECONDS)", "java.util.concurrent.TimeUnit.MILLISECONDS")
)
actual fun timer(delayMillis: Int): One<Unit> = OneTimerInt(delayMillis)
fun timer(delay: Long, unit: TimeUnit): One<Unit> = OneTimer(delay, unit)
fun Duration.asTimer(): One<Unit> = OneTimer(toMillis(), MILLISECONDS)
