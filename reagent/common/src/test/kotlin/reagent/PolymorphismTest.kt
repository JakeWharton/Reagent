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

import reagent.source.emptyObservable
import reagent.source.observableOf
import reagent.source.test.emptyMaybe
import reagent.source.test.maybeOf
import reagent.source.test.toMaybe
import reagent.source.toOne
import reagent.source.toTask
import reagent.tester.testObservable
import reagent.tester.testMaybe
import reagent.tester.testOne
import reagent.tester.testTask
import kotlin.test.Test

class PolymorphismTest {
  @Test fun taskComplete() = runTest {
    val task = emptyObservable()
    task.testTask {
      complete()
    }
    task.testMaybe {
      nothing()
    }
    task.testObservable {
      complete()
    }
  }

  @Test fun taskError() = runTest {
    val exception = RuntimeException("Oops")
    val task = exception.toTask()
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

  @Test fun oneItem() = runTest {
    val one = observableOf("Hello")
    one.testOne {
      item("Hello")
    }
    one.testMaybe {
      item("Hello")
    }
    one.testObservable {
      item("Hello")
      complete()
    }
  }

  @Test fun oneError() = runTest {
    val exception = RuntimeException("Oops")
    val one = exception.toOne<Any>()
    one.testOne {
      error(exception)
    }
    one.testMaybe {
      error(exception)
    }
    one.testObservable {
      error(exception)
    }
  }

  @Test fun maybeItem() = runTest {
    val maybe = maybeOf("Hello")
    maybe.testMaybe {
      item("Hello")
    }
    maybe.testObservable {
      item("Hello")
      complete()
    }
  }

  @Test fun maybeNothing() = runTest {
    val maybe = emptyMaybe<Any>()
    maybe.testMaybe {
      nothing()
    }
    maybe.testObservable {
      complete()
    }
  }

  @Test fun maybeError() = runTest {
    val exception = RuntimeException("Oops")
    val maybe = exception.toMaybe<Any>()
    maybe.testMaybe {
      error(exception)
    }
    maybe.testObservable {
      error(exception)
    }
  }
}
