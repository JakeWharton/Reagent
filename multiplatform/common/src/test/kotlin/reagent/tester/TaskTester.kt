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

import reagent.Disposable
import reagent.Task
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

class TaskAsserter(private val events: MutableList<Any>) {
  fun complete() {
    assertSame(Complete, events.removeAt(0))
  }

  fun error(t: Throwable) {
    assertEquals(Error(t), events.removeAt(0))
  }
}

fun Task.testTask(assertions: TaskAsserter.() -> Unit) {
  // TODO switch to something that can block for elements.
  val events = mutableListOf<Any>()
  subscribe(object : Task.Subscriber {
    override fun onSubscribe(disposable: Disposable) {
    }

    override fun onComplete() {
      events.add(Complete)
    }

    override fun onError(t: Throwable) {
      events.add(Error(t))
    }
  })

  TaskAsserter(events).assertions()

  assertTrue(events.isEmpty(), "Unconsumed events: $events")
}
