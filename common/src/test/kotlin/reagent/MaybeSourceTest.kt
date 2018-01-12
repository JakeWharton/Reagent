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

import reagent.tester.testMaybe
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MaybeSourceTest {
  @Test fun just() = runTest {
    Maybe.just("Hello")
        .testMaybe {
          item("Hello")
        }
  }

  @Test fun empty() = runTest {
    Maybe.empty<Any>()
        .testMaybe {
          nothing()
        }
  }

  @Test fun error() = runTest {
    val exception = RuntimeException("Oops!")
    Maybe.error<Any>(exception)
        .testMaybe {
          error(exception)
        }
  }

  @Test fun returning() = runTest {
    var called = false
    Maybe.returning { called = true; 0 }
        .testMaybe {
          item(0)
        }
    assertTrue(called)
  }

  @Test fun returningThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    Maybe.returning { throw exception }
        .testMaybe {
          error(exception)
        }
  }

  @Test fun running() = runTest {
    var called = false
    Maybe.running<Any> { called = true }
        .testMaybe {
          nothing()
        }
    assertTrue(called)
  }

  @Test fun runningThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    Maybe.running<Any> { throw exception }
        .testMaybe {
          error(exception)
        }
  }

  @Test fun defer() = runTest {
    var called = 0
    val deferred = Maybe.defer { called++; Maybe.just("Hello") }
    deferred.testMaybe {
      item("Hello")
    }
    assertEquals(1, called)
    deferred.testMaybe {
      item("Hello")
    }
    assertEquals(2, called)
  }
}
