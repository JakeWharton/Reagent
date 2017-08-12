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
@file:JvmName("Ones")
package reagent

import java.util.concurrent.Callable

@JvmName("fromCallable")
fun <I> Callable<I>.asOne(): One<I> = OneFromCallable(this)

internal class OneFromCallable<out I>(private val func: Callable<I>) : One<I>() {
  override fun subscribe(listener: Listener<I>) {
    val value: I
    try {
      value = func.call()
    } catch (t: Throwable) {
      listener.onError(t)
      return
    }
    listener.onItem(value)
  }
}
