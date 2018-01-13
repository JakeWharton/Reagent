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
import reagent.tester.testTask
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TaskSourceTest {
  @Test fun empty() = runTest {
    emptyTask()
        .testTask {
          complete()
        }
  }

  @Test fun error() = runTest {
    val exception = RuntimeException("Oops")
    exception.toTask()
        .testTask {
          error(exception)
        }
  }

  @Test fun running() = runTest {
    var called = false
    taskRunning { called = true }
        .testTask {
          complete()
        }
    assertTrue(called)
  }

  @Test fun runningThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    taskRunning { throw exception }
        .testTask {
          error(exception)
        }
  }

  @Test fun defer() = runTest {
    var called = 0
    val deferred = deferTask { called++; emptyTask() }
    deferred.testTask {
      complete()
    }
    assertEquals(1, called)
    deferred.testTask {
      complete()
    }
    assertEquals(2, called)
  }
}
