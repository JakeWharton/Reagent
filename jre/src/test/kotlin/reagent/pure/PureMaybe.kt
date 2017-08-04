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

import reagent.Maybe

/**
 * A guaranteed non-specialized version of `Maybe`. Calling factory methods directly on `Maybe`
 * may return a specialized implementation that's actually a `One` or `Task` which means you may
 * not actually be testing `Maybe` behavior.
 */
class PureMaybe<T>
private constructor(private val t: Throwable? = null, private val item: T? = null) : Maybe<T>() {
  override fun subscribe(listener: Listener<T>) {
    if (t != null) {
      listener.onError(t)
    } else if (item != null) {
      listener.onItem(item)
    } else {
      listener.onNothing()
    }
  }

  companion object {
    fun empty(): Maybe<Nothing> = PureMaybe()
    fun error(t: Throwable): Maybe<Nothing> = PureMaybe(t)
    fun <T> just(item: T): Maybe<T> = PureMaybe(null, item)
  }
}
