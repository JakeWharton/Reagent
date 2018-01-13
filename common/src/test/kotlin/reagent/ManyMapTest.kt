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

import reagent.operator.map
import reagent.source.emptyMany
import reagent.source.manyOf
import reagent.source.toMany
import reagent.tester.testMany
import kotlin.test.Test

class ManyMapTest {
  @Test fun map() = runTest {
    manyOf("Hello", "World")
        .map(String::toUpperCase)
        .testMany {
          item("HELLO")
          item("WORLD")
          complete()
        }
  }

  @Test fun mapEmpty() = runTest {
    emptyMany<Nothing>()
        .map { throw AssertionError() }
        .testMany {
          complete()
        }
  }

  @Test fun mapError() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toMany<Nothing>()
        .map { throw AssertionError() }
        .testMany {
          error(exception)
        }
  }

  @Test fun mapThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    manyOf("Hello", "World")
        .map { throw exception }
        .testMany {
          error(exception)
        }
  }
}
