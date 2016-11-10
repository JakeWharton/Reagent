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
import reagent.tester.testMaybe
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicBoolean

class MaybeSourceTest {
  @Test fun just() {
    Maybe.just("Hello")
        .testMaybe {
          item("Hello")
        }
  }

  @Test fun empty() {
    Maybe.empty<Any>()
        .testMaybe {
          nothing()
        }
  }

  @Test fun error() {
    val exception = RuntimeException("Oops!")
    Maybe.error<Any>(exception)
        .testMaybe {
          error(exception)
        }
  }

  @Test fun returning() {
    val called = AtomicBoolean()
    Maybe.returning { called.getAndSet(true) }
        .testMaybe {
          item(false)
        }
    assertTrue(called.get())
  }

  @Test fun returningThrowing() {
    val exception = RuntimeException("Oops!")
    Maybe.returning { throw exception }
        .testMaybe {
          error(exception)
        }
  }

  @Test fun fromCallable() {
    val called = AtomicBoolean()
    Maybe.fromCallable(Callable { called.getAndSet(true) })
        .testMaybe {
          item(false)
        }
    assertTrue(called.get())
  }

  @Test fun fromCallableThrowing() {
    val exception = RuntimeException("Oops!")
    Maybe.fromCallable(Callable { throw exception })
        .testMaybe {
          error(exception)
        }
  }

  @Test fun running() {
    val called = AtomicBoolean()
    Maybe.running<Any> { called.set(true) }
        .testMaybe {
          nothing()
        }
    assertTrue(called.get())
  }

  @Test fun runningThrowing() {
    val exception = RuntimeException("Oops!")
    Maybe.running<Any> { throw exception }
        .testMaybe {
          error(exception)
        }
  }

  @Test fun fromRunnable() {
    val called = AtomicBoolean()
    Maybe.fromRunnable<Any>(Runnable { called.set(true) })
        .testMaybe {
          nothing()
        }
    assertTrue(called.get())
  }

  @Test fun fromRunnableThrowing() {
    val exception = RuntimeException("Oops!")
    Maybe.fromRunnable<Any>(Runnable { throw exception })
        .testMaybe {
          error(exception)
        }
  }
}
