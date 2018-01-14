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
import reagent.source.toOne
import reagent.tester.testOne
import kotlin.test.Test
import kotlin.test.fail

class OneMapTest {
  @Test fun map() = runTest {
    observableOf("Hello")
        .map(String::toUpperCase)
        .testOne {
          item("HELLO")
        }
  }

  @Test fun mapError() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toOne<Nothing>()
        .map { fail() }
        .testOne {
          error(exception)
        }
  }

  @Test fun mapThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    observableOf("Hello")
        .map { throw exception }
        .testOne {
          error(exception)
        }
  }
}
