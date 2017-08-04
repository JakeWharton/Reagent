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

import org.junit.Assert
import org.junit.Test
import reagent.tester.testMany
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicBoolean

class ManySourceTest {
  @Test fun just() {
    Many.just("Hello")
        .testMany {
          item("Hello")
          complete()
        }
  }

  @Test fun from() {
    Many.fromArray("Hello", "World")
        .testMany {
          item("Hello")
          item("World")
          complete()
        }
  }

  @Test fun empty() {
    Many.empty<Any>()
        .testMany {
          complete()
        }
  }

  @Test fun error() {
    val exception = RuntimeException("Oops!")
    Many.error<Any>(exception)
        .testMany {
          error(exception)
        }
  }

  @Test fun returning() {
    val called = AtomicBoolean()
    Many.returning { called.getAndSet(true) }
        .testMany {
          item(false)
          complete()
        }
    Assert.assertTrue(called.get())
  }

  @Test fun returningThrowing() {
    val exception = RuntimeException("Oops!")
    Many.returning { throw exception }
        .testMany {
          error(exception)
        }
  }

  @Test fun running() {
    val called = AtomicBoolean()
    Many.running<Any> { called.set(true) }
        .testMany {
          complete()
        }
    Assert.assertTrue(called.get())
  }

  @Test fun runningThrowing() {
    val exception = RuntimeException("Oops!")
    Many.running<Any> { throw exception }
        .testMany {
          error(exception)
        }
  }
}
