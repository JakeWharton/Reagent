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

/** Emits a single item or errors. */
abstract class One<out I> : Maybe<I>() {
  interface Observer<in I> {
    suspend fun onItem(item: I)
    suspend fun onError(t: Throwable)
  }

  abstract suspend fun subscribe(observer: Observer<I>)
  override suspend fun subscribe(observer: Maybe.Observer<I>) = subscribe(ObserverFromMaybe(observer))
  override suspend fun subscribe(observer: Many.Observer<I>) = subscribe(ObserverFromMany(observer))

  companion object Factory {
    //@JvmStatic
    fun <I> just(item: I): One<I> = Just(item)
    //@JvmStatic
    fun <I> error(t: Throwable): One<I> = Error(t)
    //@JvmStatic
    fun <I> returning(func: () -> I): One<I> = FromLambda(func)
    //@JvmStatic
    fun <I> defer(func: () -> One<I>): One<I> = Deferred(func)
  }

  internal class Just<out I>(private val item: I) : One<I>() {
    override suspend fun subscribe(observer: Observer<I>) = observer.onItem(item)
  }

  internal class Error<out I>(private val t: Throwable) : One<I>() {
    override suspend fun subscribe(observer: Observer<I>) = observer.onError(t)
  }

  internal class FromLambda<out I>(private val func: () -> I) : One<I>() {
    override suspend fun subscribe(observer: Observer<I>) {
      val value: I
      try {
        value = func.invoke()
      } catch (t: Throwable) {
        observer.onError(t)
        return
      }
      observer.onItem(value)
    }
  }

  internal class Deferred<out I>(private val func: () -> One<I>) : One<I>() {
    override suspend fun subscribe(observer: Observer<I>) = func().subscribe(observer)
  }

  internal class ObserverFromMaybe<in U>(private val delegate: Maybe.Observer<U>) : Observer<U> {
    override suspend fun onItem(item: U) = delegate.onItem(item)
    override suspend fun onError(t: Throwable) = delegate.onError(t)
  }

  internal class ObserverFromMany<in I>(private val delegate: Many.Observer<I>): Observer<I> {
    override suspend fun onItem(item: I) = delegate.run {
      onNext(item)
      onComplete()
    }
    override suspend fun onError(t: Throwable) = delegate.onError(t)
  }
}
