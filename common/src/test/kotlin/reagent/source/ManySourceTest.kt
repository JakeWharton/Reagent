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

import reagent.runTest
import reagent.tester.testMany
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ManySourceTest {
  @Test fun just() = runTest {
    manyOf("Hello")
        .testMany {
          item("Hello")
          complete()
        }
  }

  @Test fun fromArray() = runTest {
    manyOf("Hello", "World")
        .testMany {
          item("Hello")
          item("World")
          complete()
        }
  }

  @Test fun arrayToMany() = runTest {
    arrayOf("Hello", "World")
        .toMany()
        .testMany {
          item("Hello")
          item("World")
          complete()
        }
  }

  @Test fun iterableToMany() = runTest {
    listOf("Hello", "World")
        .toMany()
        .testMany {
          item("Hello")
          item("World")
          complete()
        }
  }

  @Test fun sequenceToMany() = runTest {
    sequenceOf(1, 2, 4, 8)
        .toMany()
        .testMany {
          item(1)
          item(2)
          item(4)
          item(8)
          complete()
        }
  }

  @Test fun empty() = runTest {
    emptyMany<Any>()
        .testMany {
          complete()
        }
  }

  @Test fun error() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toMany<Any>()
        .testMany {
          error(exception)
        }
  }

  @Test fun returning() = runTest {
    var called = false
    manyReturning { called = true; 0 }
        .testMany {
          item(0)
          complete()
        }
    assertTrue(called)
  }

  @Test fun returningThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    manyReturning { throw exception }
        .testMany {
          error(exception)
        }
  }

  @Test fun running() = runTest {
    var called = false
    manyRunning<Any> { called = true }
        .testMany {
          complete()
        }
    assertTrue(called)
  }

  @Test fun runningThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    manyRunning<Any> { throw exception }
        .testMany {
          error(exception)
        }
  }

  @Test fun defer() = runTest {
    var called = 0
    val deferred = deferMany { called++; manyOf("Hello") }
    deferred.testMany {
      item("Hello")
      complete()
    }
    assertEquals(1, called)
    deferred.testMany {
      item("Hello")
      complete()
    }
    assertEquals(2, called)
  }

  @Test fun intProgression() = runTest {
    (10 downTo 1 step 3).toMany()
        .testMany {
          item(10)
          item(7)
          item(4)
          item(1)
          complete()
        }
  }

  @Test fun longProgression() = runTest {
    (10L downTo 1L step 3L).toMany()
        .testMany {
          item(10L)
          item(7L)
          item(4L)
          item(1L)
          complete()
        }
  }

  @Test fun charProgression() = runTest {
    ('z' downTo 'a' step 5).toMany()
        .testMany {
          item('z')
          item('u')
          item('p')
          item('k')
          item('f')
          item('a')
          complete()
        }
  }
}
