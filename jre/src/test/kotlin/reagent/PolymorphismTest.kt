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

import org.junit.Test
import reagent.tester.testMany
import reagent.tester.testMaybe
import reagent.tester.testOne
import reagent.tester.testTask

class PolymorphismTest {
  @Test fun taskComplete() {
    val task = Task.empty()
    task.testTask {
      complete()
    }
    task.testMaybe {
      nothing()
    }
    task.testMany {
      complete()
    }
  }

  @Test fun taskError() {
    val exception = RuntimeException("Oops")
    val task = Task.error(exception)
    task.testTask {
      error(exception)
    }
    task.testMaybe {
      error(exception)
    }
    task.testMaybe {
      error(exception)
    }
  }

  @Test fun oneItem() {
    val one = One.just("Hello")
    one.testOne {
      item("Hello")
    }
    one.testMaybe {
      item("Hello")
    }
    one.testMany {
      item("Hello")
      complete()
    }
  }

  @Test fun oneError() {
    val exception = RuntimeException("Oops")
    val one = One.error<Any>(exception)
    one.testOne {
      error(exception)
    }
    one.testMaybe {
      error(exception)
    }
    one.testMany {
      error(exception)
    }
  }

  @Test fun maybeItem() {
    val maybe = Maybe.just("Hello")
    maybe.testMaybe {
      item("Hello")
    }
    maybe.testMany {
      item("Hello")
      complete()
    }
  }

  @Test fun maybeNothing() {
    val maybe = Maybe.empty<Any>()
    maybe.testMaybe {
      nothing()
    }
    maybe.testMany {
      complete()
    }
  }

  @Test fun maybeError() {
    val exception = RuntimeException("Oops")
    val maybe = Maybe.error<Any>(exception)
    maybe.testMaybe {
      error(exception)
    }
    maybe.testMany {
      error(exception)
    }
  }
}
