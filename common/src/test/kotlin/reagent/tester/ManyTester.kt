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

import reagent.Many
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ManyAsserter<T>(private val events: MutableList<Any>) {
  fun item(item: T) {
    assertEquals(Item(item), events.removeAt(0))
  }

  fun complete() {
    assertTrue(Complete === events.removeAt(0))
    // TODO switch to assertSame once https://github.com/JetBrains/kotlin/pull/1230 is released.
  }

  fun error(t: Throwable) {
    assertEquals(Error(t), events.removeAt(0))
  }
}

fun <T> Many<T>.testMany(assertions: ManyAsserter<T>.() -> Unit) {
  // TODO switch to something that can block for elements.
  val events = mutableListOf<Any>()
  subscribe(object : Many.Listener<T> {
    override fun onNext(item: T) {
      events.add(Item(item))
    }

    override fun onComplete() {
      events.add(Complete)
    }

    override fun onError(t: Throwable) {
      events.add(Error(t))
    }
  })

  ManyAsserter<T>(events).assertions()

  assertTrue(events.isEmpty(), "Unconsumed events: $events")
}
