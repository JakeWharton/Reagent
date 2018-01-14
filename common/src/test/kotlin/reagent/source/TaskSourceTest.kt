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
import reagent.Task
import reagent.runTest
import reagent.tester.testObservable
import reagent.tester.testTask
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TaskSourceTest {
  @Test fun suspendingLambda() = runTest {
    task {
      delay(10)
    }.testTask {
      complete()
    }
  }

  @Test fun empty() = runTest {
    emptyObservable()
        .testTask {
          complete()
        }
  }

  @Test fun noItems() = runTest {
    observableOf()
        .testTask {
          complete()
        }
  }

  @Test fun throwable() = runTest {
    val exception = RuntimeException("Oops")
    exception.toTask()
        .testTask {
          error(exception)
        }
  }

  @Test fun running() = runTest {
    var called = false
    observableRunning { called = true }
        .testTask {
          complete()
        }
    assertTrue(called)
  }

  @Test fun runningThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    observableRunning { throw exception }
        .testTask {
          error(exception)
        }
  }

  @Test fun defer() = runTest {
    var called = 0
    val deferred = deferTask { called++; emptyObservable() }
    deferred.testTask {
      complete()
    }
    assertEquals(1, called)
    deferred.testTask {
      complete()
    }
    assertEquals(2, called)
  }

  @Test fun concatEmpty() = runTest {
    concat(emptyList())
        .testTask {
          complete()
        }
  }

  @Test fun concat() = runTest {
    var called = 0
    concat(
        observableRunning { called++ },
        observableRunning { called++ },
        observableRunning { called++ },
        observableRunning { called++ }
    ).testObservable {
      complete()
    }
    assertEquals(4, called)
  }
}
