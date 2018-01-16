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
import reagent.source.observableRunning
import reagent.source.test.emptyMaybe
import reagent.source.test.failTask
import reagent.source.test.maybeOf
import reagent.source.test.toMaybe
import reagent.tester.testTask
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MaybeFlatMapTest {
// TODO overload resolution doesn't work here
//  @Test fun flatMapObservable() = runTest {
//    maybeOf("Item")
//        .flatMap { observableOf("Hello", "World") }
//        .testObservable {
//          item("Hello")
//          item("World")
//          complete()
//        }
//  }
//
//  @Test fun flatMapObservableNothing() = runTest {
//    emptyMaybe<String>()
//        .flatMap { observableOf("Hello", "World") }
//        .testObservable {
//          complete()
//        }
//  }
//
//  @Test fun flatMapObservableError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toMaybe<String>()
//        .flatMap { failObservable<String>() }
//        .testObservable {
//          error(exception)
//        }
//  }
//
//  @Test fun flatMapMaybe() = runTest {
//    maybeOf("Item")
//        .flatMap { maybeOf("Hello") }
//        .testMaybe {
//          item("Hello")
//        }
//  }
//
//  @Test fun flatMapMaybeNothing() = runTest {
//    emptyMaybe<String>()
//        .flatMap { maybeOf("Hello") }
//        .testMaybe {
//          nothing()
//        }
//  }
//
//  @Test fun flatMapMaybeError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toMaybe<String>()
//        .flatMap { failMaybe<String>() }
//        .testMaybe {
//          error(exception)
//        }
//  }
//
//  @Test fun flatMapOne() = runTest {
//    maybeOf("Item")
//        .flatMap { oneOf("Hello") }
//        .testMaybe {
//          item("Hello")
//        }
//  }
//
//  @Test fun flatMapOneComplete() = runTest {
//    emptyMaybe<String>()
//        .flatMap { oneOf("Hello") }
//        .testMaybe {
//          nothing()
//        }
//  }
//
//  @Test fun flatMapOneError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toMaybe<String>()
//        .flatMap { failOne<String>() }
//        .testMaybe {
//          error(exception)
//        }
//  }

  @Test fun flatMapTask() = runTest {
    var called = false
    maybeOf("Item")
        .flatMap { observableRunning { called = true } }
        .testTask {
          complete()
        }
    assertTrue(called)
  }

  @Test fun flatMapTaskComplete() = runTest {
    var called = false
    emptyMaybe<String>()
        .flatMap { observableRunning { called = true } }
        .testTask {
          complete()
        }
    assertFalse(called)
  }

  @Test fun flatMapTaskError() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toMaybe<String>()
        .flatMap { failTask() }
        .testTask {
          error(exception)
        }
  }
}
