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

import reagent.source.observableOf
import reagent.source.toTask
import reagent.tester.testObservable
import reagent.tester.testTask
import kotlin.test.Test

class PolymorphismTest {
  @Test fun oneItem() = runTest {
    val one = observableOf("Hello")
    one.testTask {
      item("Hello")
    }
    one.testObservable {
      item("Hello")
      complete()
    }
  }

  @Test fun oneError() = runTest {
    val exception = RuntimeException("Oops")
    val one = exception.toTask()
    one.testTask {
      error(exception)
    }
    one.testObservable {
      error(exception)
    }
  }
}
