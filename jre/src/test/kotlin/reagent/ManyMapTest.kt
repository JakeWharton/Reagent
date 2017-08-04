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

import org.junit.Ignore
import org.junit.Test
import reagent.pure.PureMany
import reagent.tester.testMany

class ManyMapTest {
  @Test fun map() {
    PureMany.just("Hello", "World")
        .map(String::toUpperCase)
        .testMany {
          item("HELLO")
          item("WORLD")
          complete()
        }
  }

  @Test fun mapEmpty() {
    PureMany.empty()
        .map { throw AssertionError() }
        .testMany {
          complete()
        }
  }

  @Test fun mapError() {
    val exception = RuntimeException("Oops!")
    PureMany.error(exception)
        .map { throw AssertionError() }
        .testMany {
          error(exception)
        }
  }

  @Ignore("Error handling not implemented yet")
  @Test fun mapThrowing() {
    val exception = RuntimeException("Oops!")
    PureMany.just("Hello", "World")
        .map { throw exception }
        .testMany {
          error(exception)
        }
  }
}
