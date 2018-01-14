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
package reagent.operator

import reagent.runTest
import reagent.source.observableOf
import reagent.source.test.emptyMany
import reagent.source.test.toMany
import reagent.tester.testObservable
import kotlin.test.Test
import kotlin.test.fail

class ObservableMapTest {
  @Test fun map() = runTest {
    observableOf("Hello", "World")
        .map(String::toUpperCase)
        .testObservable {
          item("HELLO")
          item("WORLD")
          complete()
        }
  }

  @Test fun mapEmpty() = runTest {
    emptyMany<Nothing>()
        .map { fail() }
        .testObservable {
          complete()
        }
  }

  @Test fun mapError() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toMany<Nothing>()
        .map { fail() }
        .testObservable {
          error(exception)
        }
  }

  @Test fun mapThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    observableOf("Hello", "World")
        .map { throw exception }
        .testObservable {
          error(exception)
        }
  }
}
