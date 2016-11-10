/*
 * Copyright 2016 Jake Wharton
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
package reagent.pure

import reagent.Many

/**
 * A guaranteed non-specialized version of `Many`. Calling factory methods directly on `Many`
 * may return a specialized implementation that's actually a `Maybe`, `One`, or `Task` which means
 * you may not actually be testing `Many` behavior.
 */
class PureMany<T>
private constructor(private val t: Throwable? = null, private vararg val items: T) : Many<T>() {
  override fun subscribe(listener: Listener<T>) {
    if (t != null) {
      listener.onError(t)
    } else {
      items.forEach {
        listener.onNext(it)
      }
      listener.onComplete()
    }
  }

  companion object {
    fun empty(): Many<Nothing> = PureMany()
    fun error(t: Throwable): Many<Nothing> = PureMany(t)
    fun <T> just(vararg items: T): Many<T> = PureMany(null, *items)
  }
}
