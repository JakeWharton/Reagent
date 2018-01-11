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
import reagent.Many
import reagent.Many.Emitter
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

class ManyAsserter<T>(private val events: MutableList<Any>) {
  fun item(item: T) {
    assertEquals(Item(item), events.removeAt(0))
  }

  fun complete() {
    assertSame(Complete, events.removeAt(0))
  }

  fun error(t: Throwable) {
    assertEquals(Error(t), events.removeAt(0))
  }
}

fun <T> Many<T>.testMany(assertions: ManyAsserter<T>.() -> Unit) {
  val events = mutableListOf<Any>()

  runBlocking {
    try {
      subscribe(object : Emitter<T> {
        override suspend fun send(item: T) {
          events.add(Item(item))
        }
      })
      events.add(Complete)
    } catch (t: Throwable) {
      events.add(Error(t))
    }
  }

  ManyAsserter<T>(events).assertions()

  assertTrue(events.isEmpty(), "Unconsumed events: $events")
}
