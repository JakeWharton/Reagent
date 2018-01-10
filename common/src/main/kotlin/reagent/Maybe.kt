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
package reagent

/** Emits an item, signals nothing (no item), or signals error. */
abstract class Maybe<out I> : Many<I>() {
  interface Observer<in I> {
    suspend fun onItem(item: I)
    suspend fun onNothing()
    suspend fun onError(t: Throwable)
  }

  abstract suspend fun subscribe(observer: Observer<I>)
  override suspend fun subscribe(observer: Many.Observer<I>) = subscribe(ObserverFromMany(observer))

  companion object Factory {
    //@JvmStatic
    fun <I> just(item: I) : Maybe<I> = One.Just(item)
    //@JvmStatic
    fun <I> empty() : Maybe<I> = Task.Complete
    //@JvmStatic
    fun <I> error(t: Throwable): Maybe<I> = Task.Error(t)
    //@JvmStatic
    fun <I> returning(func: () -> I): Maybe<I> = One.FromLambda(func)
    //@JvmStatic
    fun <I> running(func: () -> Unit): Maybe<I> = Task.FromLambda(func)
    //@JvmStatic
    fun <I> defer(func: () -> Maybe<I>): Maybe<I> = Deferred(func)
  }

  internal class Deferred<out I>(private val func: () -> Maybe<I>): Maybe<I>() {
    override suspend fun subscribe(observer: Observer<I>) = func().subscribe(observer)
  }

  internal class ObserverFromMany<in U>(private val delegate: Many.Observer<U>) : Observer<U> {
    override suspend fun onItem(item: U) = delegate.run {
      onNext(item)
      onComplete()
    }
    override suspend fun onNothing() = delegate.onComplete()
    override suspend fun onError(t: Throwable) = delegate.onError(t)
  }
}
