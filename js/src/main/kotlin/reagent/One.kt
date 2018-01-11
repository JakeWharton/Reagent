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
package reagent

import kotlin.js.Promise
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.suspendCoroutine

@JsName("fromPromise")
fun <I> Promise<I>.toOne(): One<I> = OneFromPromise(this)

internal class OneFromPromise<out I>(private val promise: Promise<I>): One<I>() {
  override suspend fun produce() = suspendCoroutine<I> { continuation ->
    promise.then(continuation::resume, continuation::resumeWithException)
  }
}

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
