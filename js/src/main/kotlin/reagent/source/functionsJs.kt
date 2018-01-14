/*
 * Copyright 2017 Google, Inc.
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

import kotlin.js.Promise
import kotlinx.coroutines.experimental.launch
import reagent.Observable
import reagent.One

actual fun interval(periodMillis: Int): Observable<Int> = ObservableIntervalInt(periodMillis)
actual fun timer(delayMillis: Int): One<Unit> = OneTimerInt(delayMillis)

fun <I> Promise<I>.toOne(): One<I> = OneFromPromise(this)

fun <I> One<I>.toPromise(): Promise<I> = Promise { resolve, reject ->
  launch {
    val result = try {
      produce()
    } catch (t: Throwable) {
      reject(t)
      return@launch
    }
    resolve(result)
  }
}
