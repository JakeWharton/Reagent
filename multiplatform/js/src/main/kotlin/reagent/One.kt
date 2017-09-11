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

@JsName("fromPromise")
fun <I> Promise<I>.toOne(): One<I> = OneFromPromise(this)

internal class OneFromPromise<out I>(private val promise: Promise<I>): One<I>() {
  override fun subscribe(subscriber: Subscriber<I>) {
    val disposable = Disposable.empty()
    promise.then({
      if (!disposable.isDisposed) {
        subscriber.onItem(it)
      }
    }, {
      if (!disposable.isDisposed) {
        subscriber.onError(it)
      }
    })
  }
}

fun <I> One<I>.toPromise(): Promise<I> = Promise { resolve, reject ->
  subscribe(object : One.Subscriber<I> {
    override fun onSubscribe(disposable: Disposable) {
      // TODO retain the instance?
    }

    override fun onError(t: Throwable) = reject(t)
    override fun onItem(item: I) = resolve(item)
  })
}
