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
import reagent.operator.map
import reagent.tester.testOne

class OneMapTest {
  @Test fun map() {
    One.just("Hello")
        .map(String::toUpperCase)
        .testOne {
          item("HELLO")
        }
  }

  @Test fun mapError() {
    val exception = RuntimeException("Oops!")
    One.error<Nothing>(exception)
        .map { throw AssertionError() }
        .testOne {
          error(exception)
        }
  }

//  @Ignore("Error handling not implemented yet")
//  @Test fun mapThrowing() {
//    val exception = RuntimeException("Oops!")
//    PureOne.just("Hello")
//        .map { throw exception }
//        .testOne {
//          error(exception)
//        }
//  }
}
