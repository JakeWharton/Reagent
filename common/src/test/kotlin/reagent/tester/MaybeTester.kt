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
package reagent.tester

import reagent.runBlocking
import reagent.Maybe
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

class MaybeAsserter<T>(private val events: MutableList<Any>) {
  fun item(item: T) {
    assertEquals(Item(item), events.removeAt(0))
  }

  fun nothing() {
    assertSame(Complete, events.removeAt(0))
  }

  fun error(t: Throwable) {
    assertEquals(Error(t), events.removeAt(0))
  }
}

fun <T> Maybe<T>.testMaybe(assertions: MaybeAsserter<T>.() -> Unit) {
  val events = mutableListOf<Any>()

  runBlocking {
    subscribe(object : Maybe.Observer<T> {
      override suspend fun onItem(item: T) {
        events.add(Item(item))
      }

      override suspend fun onNothing() {
        events.add(Complete)
      }

      override suspend fun onError(t: Throwable) {
        events.add(Error(t))
      }
    })
  }

  MaybeAsserter<T>(events).assertions()

  assertTrue(events.isEmpty(), "Unconsumed events: $events")
}
