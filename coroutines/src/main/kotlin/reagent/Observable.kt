/*
 * Copyright (C) 2017 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package reagent

import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.produce
import kotlinx.coroutines.experimental.runBlocking
import java.util.function.Consumer
import kotlin.coroutines.experimental.CoroutineContext

abstract class Observable<T> {
  suspend abstract fun subscribe(context: CoroutineContext): ReceiveChannel<T>

  @JvmOverloads
  fun subscribe(
      onNext: Consumer<T>,
      onError: Consumer<Throwable> = NoLambda,
      onComplete: Runnable = NoLambda
  ) = runBlocking(Unconfined) {
    try {
      for (value in subscribe(coroutineContext)) {
        onNext.accept(value)
      }
      onComplete.run()
    } catch (t: Throwable) {
      if (t is Error) {
        throw t
      }
      onError.accept(t)
    }
  }

  private object NoLambda : Consumer<Throwable>, Runnable {
    override fun accept(t: Throwable) = Unit
    override fun run() = Unit
  }

  fun <O> map(mapper: (T) -> O): Observable<O> = Map(this, mapper)
  fun filter(predicate: (T) -> Boolean): Observable<T> = Filter(this, predicate)
  fun take(count: Int): Observable<T> = Take(this, count)
  fun skip(count: Int): Observable<T> = Skip(this, count)
  fun <R> collect(collectionSupplier: () -> R, collector: (R, T) -> Unit): Observable<R> = Collect(this, collectionSupplier, collector)
  fun <O> flatten(mapper: (T) -> Iterable<O>): Observable<O> = Flatten(this, mapper)
  fun concat(vararg sources: Observable<T>): Observable<T> = Concat(arrayOf(this, *sources))

  companion object {
    @JvmStatic
    fun <O> just(value: O): Observable<O> = FromValue(value)
    @JvmStatic
    fun <O> fromIterable(iterable: Iterable<O>): Observable<O> = FromIterable(iterable)
  }
}

// TODO blocked by https://youtrack.jetbrains.com/issue/KT-10468
//suspend fun <T> (Observable<T>, CoroutineScope).subscribe() = subscribe(coroutineContext)

class Chars(private val string: String): Observable<Int>() {
  suspend override fun subscribe(context: CoroutineContext) = produce(context) {
    for (char in string) {
      send(char.toInt())
    }
  }
}

private class FromValue<O>(private val value: O) : Observable<O>() {
  suspend override fun subscribe(context: CoroutineContext) = produce(context) { send(value) }
}

private class FromIterable<O>(private val iterable: Iterable<O>): Observable<O>() {
  suspend override fun subscribe(context: CoroutineContext) = produce(context) {
    iterable.forEach { send(it) }
  }
}

private class Map<in U, D>(
    private val upstream: Observable<U>,
    private val mapper: (U) -> D
) : Observable<D>() {
  suspend override fun subscribe(context: CoroutineContext) = produce(context) {
    for (value in upstream.subscribe(context)) {
      send(mapper(value))
    }
  }
}

private class Filter<O>(
    private val upstream: Observable<O>,
    private val predicate: (O) -> Boolean
) : Observable<O>() {
  suspend override fun subscribe(context: CoroutineContext) = produce(context) {
    for (value in upstream.subscribe(context)) {
      if (predicate(value)) {
        send(value)
      }
    }
  }
}

private class Take<O>(
    private val upstream: Observable<O>,
    private val count: Int
) : Observable<O>() {
  suspend override fun subscribe(context: CoroutineContext) = produce(context) {
    var seen = 0
    for (value in upstream.subscribe(context)) {
      send(value)
      if (++seen == count) {
        break
      }
    }
  }
}

private class Skip<O>(
    private val upstream: Observable<O>,
    private val count: Int
) : Observable<O>() {
  suspend override fun subscribe(context: CoroutineContext) = produce(context) {
    var seen = 0
    for (value in upstream.subscribe(context)) {
      if (++seen > count) {
        send(value)
      }
    }
  }
}

private class Collect<T, R>(
    private val upstream: Observable<T>,
    private val collectionSupplier: () -> R,
    private val collector: (R, T) -> Unit
) : Observable<R>() {
  suspend override fun subscribe(context: CoroutineContext) = produce(context) {
    val collection = collectionSupplier()
    for (value in upstream.subscribe(context)) {
      collector(collection, value)
    }
    send(collection)
  }
}

private class Flatten<in U, D>(
    private val upstream: Observable<U>,
    private val mapper: (U) -> Iterable<D>
) : Observable<D>() {
  suspend override fun subscribe(context: CoroutineContext) = produce(context) {
    for (value in upstream.subscribe(context)) {
      for (inner in mapper(value)) {
        send(inner)
      }
    }
  }
}

private class Concat<O>(private val sources: Array<Observable<O>>) : Observable<O>() {
  suspend override fun subscribe(context: CoroutineContext) = produce(context) {
    for (source in sources) {
      for (value in source.subscribe(context)) {
        send(value)
      }
    }
  }
}
