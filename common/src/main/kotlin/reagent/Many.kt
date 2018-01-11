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

/** Emits 0 to infinite items and then signals complete or error. */
abstract class Many<out I> {
  interface Emitter<in I> {
    suspend fun send(item: I)
  }

  abstract suspend fun subscribe(emitter: Emitter<I>)

  companion object Factory {
    //@JvmStatic
    fun <I> just(item: I): Many<I> = One.Just(item)
    //@JvmStatic
    //@JvmName("fromArray")
    // TODO rename to 'just' once @JvmName works: https://youtrack.jetbrains.com/issue/KT-19507.
    fun <I> fromArray(vararg items: I): Many<I> = FromArray(items)
    //@JvmStatic
    fun <I> fromIterable(items: Iterable<I>): Many<I> = FromIterable(items)
    //@JvmStatic
    fun <I> empty() : Many<I> = Task.Complete
    //@JvmStatic
    fun <I> error(t: Throwable): Many<I> = One.Error(t)
    //@JvmStatic
    fun <I> returning(func: () -> I): Many<I> = One.FromLambda(func)
    //@JvmStatic
    fun <I> running(func: () -> Unit): Many<I> = Task.FromLambda(func)
    //@JvmStatic
    fun <I> defer(func: () -> Many<I>): Many<I> = Deferred(func)
  }

  internal class FromArray<out I>(private val items: Array<out I>) : Many<I>() {
    override suspend fun subscribe(emitter: Emitter<I>) {
      items.forEach { emitter.send(it) }
    }
  }

  internal class FromIterable<out I>(private val iterable: Iterable<I>): Many<I>() {
    override suspend fun subscribe(emitter: Emitter<I>) {
      iterable.forEach { emitter.send(it) }
    }
  }

  internal class Deferred<out I>(private val func: () -> Many<I>): Many<I>() {
    override suspend fun subscribe(emitter: Emitter<I>) = func().subscribe(emitter)
  }
}

// TODO move to companion object after https://youtrack.jetbrains.com/issue/KT-18416.
fun <T> Array<T>.toMany(): Many<T> = Many.FromArray(this)

// TODO move to companion object after https://youtrack.jetbrains.com/issue/KT-18416.
fun <T> Iterable<T>.toMany(): Many<T> = Many.FromIterable(this)
