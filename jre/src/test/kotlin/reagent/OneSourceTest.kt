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

import org.junit.Assert.assertTrue
import org.junit.Test
import reagent.tester.testOne
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicBoolean

class OneSourceTest {
  @Test fun just() {
    One.just("Hello")
        .testOne {
          item("Hello")
        }
  }

  @Test fun error() {
    val exception = RuntimeException("Oops!")
    One.error<Any>(exception)
        .testOne {
          error(exception)
        }
  }

  @Test fun returning() {
    val called = AtomicBoolean()
    One.returning { called.getAndSet(true) }
        .testOne {
          item(false)
        }
    assertTrue(called.get())
  }

  @Test fun returningThrowing() {
    val exception = RuntimeException("Oops!")
    One.returning { throw exception }
        .testOne {
          error(exception)
        }
  }
}
