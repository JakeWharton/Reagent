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
import reagent.operator.flatMap
import reagent.tester.testTask

class MaybeFlatMapTest {
// TODO overload resolution doesn't work here
//  @Test fun flatMapMany() {
//    PureMaybe.just("Item")
//        .flatMap { Many.fromArray("Hello", "World") }
//        .testMany {
//          item("Hello")
//          item("World")
//          complete()
//        }
//  }
//
//  @Test fun flatMapManyNothing() {
//    PureMaybe.empty()
//        .flatMap { Many.fromArray("Hello", "World") }
//        .testMany {
//          complete()
//        }
//  }
//
//  @Test fun flatMapManyError() {
//    val exception = RuntimeException("Oops!")
//    PureMaybe.error(exception)
//        .flatMap { Many.fromArray("Hello", "World") }
//        .testMany {
//          error(exception)
//        }
//  }
//
//  @Test fun flatMapMaybe() {
//    PureMaybe.just("Item")
//        .flatMap { Maybe.just("Hello") }
//        .testMaybe {
//          item("Hello")
//        }
//  }
//
//  @Test fun flatMapMaybeNothing() {
//    PureMaybe.empty()
//        .flatMap { Maybe.just("Hello") }
//        .testMaybe {
//          nothing()
//        }
//  }
//
//  @Test fun flatMapMaybeError() {
//    val exception = RuntimeException("Oops!")
//    PureMaybe.error(exception)
//        .flatMap { Maybe.just("Hello") }
//        .testMaybe {
//          error(exception)
//        }
//  }
//
//  @Test fun flatMapOne() {
//    PureMaybe.just("Item")
//        .flatMap { One.just("Hello") }
//        .testMaybe {
//          item("Hello")
//        }
//  }
//
//  @Test fun flatMapOneComplete() {
//    PureMaybe.empty()
//        .flatMap { One.just("Hello") }
//        .testMaybe {
//          nothing()
//        }
//  }
//
//  @Test fun flatMapOneError() {
//    val exception = RuntimeException("Oops!")
//    PureMaybe.error(exception)
//        .flatMap { One.just("Hello") }
//        .testMaybe {
//          error(exception)
//        }
//  }

  // TODO @Test
  fun flatMapTask() {
    Maybe.just("Item")
        .flatMap { Task.empty() }
        .testTask {
          complete()
        }
  }

  // TODO @Test
  fun flatMapTaskComplete() {
    Maybe.empty<String>()
        .flatMap { Task.empty() }
        .testTask {
          complete()
        }
  }

  // TODO @Test
  fun flatMapTaskError() {
    val exception = RuntimeException("Oops!")
    Maybe.error<String>(exception)
        .flatMap { Task.empty() }
        .testTask {
          error(exception)
        }
  }
}
