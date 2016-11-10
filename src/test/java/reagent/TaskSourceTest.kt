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
import reagent.tester.testTask
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicBoolean

class TaskSourceTest {
  @Test fun empty() {
    Task.empty()
        .testTask {
          complete()
        }
  }

  @Test fun error() {
    val exception = RuntimeException("Oops")
    Task.error(exception)
        .testTask {
          error(exception)
        }
  }

  @Test fun running() {
    val called = AtomicBoolean()
    Task.running { called.set(true) }
        .testTask {
          complete()
        }
    assertTrue(called.get())
  }

  @Test fun runningThrowing() {
    val exception = RuntimeException("Oops!")
    Task.running { throw exception }
        .testTask {
          error(exception)
        }
  }

  @Test fun fromRunnable() {
    val called = AtomicBoolean()
    Task.fromRunnable(Runnable { called.set(true) })
        .testTask {
          complete()
        }
    assertTrue(called.get())
  }

  @Test fun fromRunnableThrowing() {
    val exception = RuntimeException("Oops!")
    Task.fromRunnable(Runnable { throw exception })
        .testTask {
          error(exception)
        }
  }

  @Test fun fromCallable() {
    val called = AtomicBoolean()
    Task.fromCallable(Callable { called.set(true) })
        .testTask {
          complete()
        }
    assertTrue(called.get())
  }

  @Test fun fromCallableThrowing() {
    val exception = RuntimeException("Oops!")
    Task.fromCallable(Callable { throw exception })
        .testTask {
          error(exception)
        }
  }
}
