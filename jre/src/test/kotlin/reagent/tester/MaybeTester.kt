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

import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import reagent.Maybe
import java.util.concurrent.LinkedBlockingDeque

class MaybeAsserter<T>(private val events: LinkedBlockingDeque<Any>) {
  fun item(item: T) {
    assertEquals(Item(item), events.pollFirst())
  }

  fun nothing() {
    assertSame(Complete, events.pollFirst())
  }

  fun error(t: Throwable) {
    assertEquals(Error(t), events.pollFirst())
  }
}

fun <T> Maybe<T>.testMaybe(assertions: MaybeAsserter<T>.() -> Unit) {
  val events = LinkedBlockingDeque<Any>()
  subscribe(object : Maybe.Listener<T> {
    override fun onItem(item: T) {
      events.add(Item(item))
    }

    override fun onNothing() {
      events.add(Complete)
    }

    override fun onError(t: Throwable) {
      events.add(Error(t))
    }
  })

  MaybeAsserter<T>(events).assertions()

  assert(events.isEmpty()) { "Unconsumed events: $events" }
}
