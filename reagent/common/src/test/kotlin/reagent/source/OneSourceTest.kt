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
package reagent.source

import kotlinx.coroutines.experimental.delay
import reagent.runTest
import reagent.tester.testOne
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OneSourceTest {
  @Test fun suspendingLambda() = runTest {
    one {
      delay(10)
      "Hello"
    }.testOne {
      item("Hello")
    }
  }

  @Test fun of() = runTest {
    observableOf("Hello")
        .testOne {
          item("Hello")
        }
  }

  @Test fun throwable() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toOne()
        .testOne {
          error(exception)
        }
  }

  @Test fun returning() = runTest {
    var called = false
    observableReturning { called = true; 0 }
        .testOne {
          item(0)
        }
    assertTrue(called)
  }

  @Test fun returningThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    observableReturning { throw exception }
        .testOne {
          error(exception)
        }
  }

  @Test fun defer() = runTest {
    var called = 0
    val deferred = deferOne { called++; observableOf("Hello") }
    deferred.testOne {
      item("Hello")
    }
    assertEquals(1, called)
    deferred.testOne {
      item("Hello")
    }
    assertEquals(2, called)
  }

  @Test fun timer() = runTest {
    // TODO need virtual time context to validate this works!
    val timer = timer(100)
    timer.testOne {
      item(Unit)
    }
  }
}
