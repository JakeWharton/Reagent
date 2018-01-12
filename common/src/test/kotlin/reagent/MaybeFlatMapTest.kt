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

import reagent.operator.flatMap
import reagent.tester.testTask
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore // Not implemented
class MaybeFlatMapTest {
// TODO overload resolution doesn't work here
//  @Test fun flatMapMany() = runTest {
//    PureMaybe.just("Item")
//        .flatMap { Many.fromArray("Hello", "World") }
//        .testMany {
//          item("Hello")
//          item("World")
//          complete()
//        }
//  }
//
//  @Test fun flatMapManyNothing() = runTest {
//    PureMaybe.empty()
//        .flatMap { Many.fromArray("Hello", "World") }
//        .testMany {
//          complete()
//        }
//  }
//
//  @Test fun flatMapManyError() = runTest {
//    val exception = RuntimeException("Oops!")
//    PureMaybe.error(exception)
//        .flatMap { Many.fromArray("Hello", "World") }
//        .testMany {
//          error(exception)
//        }
//  }
//
//  @Test fun flatMapMaybe() = runTest {
//    PureMaybe.just("Item")
//        .flatMap { Maybe.just("Hello") }
//        .testMaybe {
//          item("Hello")
//        }
//  }
//
//  @Test fun flatMapMaybeNothing() = runTest {
//    PureMaybe.empty()
//        .flatMap { Maybe.just("Hello") }
//        .testMaybe {
//          nothing()
//        }
//  }
//
//  @Test fun flatMapMaybeError() = runTest {
//    val exception = RuntimeException("Oops!")
//    PureMaybe.error(exception)
//        .flatMap { Maybe.just("Hello") }
//        .testMaybe {
//          error(exception)
//        }
//  }
//
//  @Test fun flatMapOne() = runTest {
//    PureMaybe.just("Item")
//        .flatMap { One.just("Hello") }
//        .testMaybe {
//          item("Hello")
//        }
//  }
//
//  @Test fun flatMapOneComplete() = runTest {
//    PureMaybe.empty()
//        .flatMap { One.just("Hello") }
//        .testMaybe {
//          nothing()
//        }
//  }
//
//  @Test fun flatMapOneError() = runTest {
//    val exception = RuntimeException("Oops!")
//    PureMaybe.error(exception)
//        .flatMap { One.just("Hello") }
//        .testMaybe {
//          error(exception)
//        }
//  }

  @Test fun flatMapTask() = runTest {
    Maybe.just("Item")
        .flatMap { Task.empty() }
        .testTask {
          complete()
        }
  }

  @Test fun flatMapTaskComplete() = runTest {
    Maybe.empty<String>()
        .flatMap { Task.empty() }
        .testTask {
          complete()
        }
  }

  @Test fun flatMapTaskError() = runTest {
    val exception = RuntimeException("Oops!")
    Maybe.error<String>(exception)
        .flatMap { Task.empty() }
        .testTask {
          error(exception)
        }
  }
}
