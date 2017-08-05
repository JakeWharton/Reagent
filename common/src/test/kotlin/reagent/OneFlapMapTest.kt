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
import reagent.pure.PureOne
import reagent.tester.testMany
import reagent.tester.testMaybe
import reagent.tester.testOne
import reagent.tester.testTask

class OneFlapMapTest {
  @Test fun flatMapMany() {
    PureOne.just("Item")
        .flatMapMany { Many.fromArray("Hello", "World") }
        .testMany {
          item("Hello")
          item("World")
          complete()
        }
  }

  @Test fun flatMapManyError() {
    val exception = RuntimeException("Oops!")
    PureOne.error(exception)
        .flatMapMany { Many.fromArray("Hello", "World") }
        .testMany {
          error(exception)
        }
  }

  @Test fun flatMapMaybe() {
    PureOne.just("Item")
        .flatMapMaybe { Maybe.just("Hello") }
        .testMaybe {
          item("Hello")
        }
  }

  @Test fun flatMapMaybeError() {
    val exception = RuntimeException("Oops!")
    PureOne.error(exception)
        .flatMapMaybe { Maybe.just("Hello") }
        .testMaybe {
          error(exception)
        }
  }

  @Test fun flatMapOne() {
    PureOne.just("Item")
        .flatMapOne { One.just("Hello") }
        .testOne {
          item("Hello")
        }
  }

  @Test fun flatMapOneError() {
    val exception = RuntimeException("Oops!")
    PureOne.error(exception)
        .flatMapOne { One.just("Hello") }
        .testOne {
          error(exception)
        }
  }

  @Test fun flatMapTask() {
    PureOne.just("Item")
        .flatMapTask { Task.empty() }
        .testTask {
          complete()
        }
  }

  @Test fun flatMapTaskError() {
    val exception = RuntimeException("Oops!")
    PureOne.error(exception)
        .flatMapTask { Task.empty() }
        .testTask {
          error(exception)
        }
  }
}