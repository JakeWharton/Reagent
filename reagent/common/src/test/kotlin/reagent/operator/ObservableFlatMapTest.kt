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
import reagent.source.observableRunning
import reagent.source.test.emptyMany
import reagent.source.test.failTask
import reagent.source.test.toMany
import reagent.tester.testTask
import kotlin.test.Test
import kotlin.test.assertEquals

class ObservableFlatMapTest {
// TODO overload resolution doesn't work here
//  @Test fun flatMapMany() = runTest {
//    val flatMapItems = mutableListOf<String>()
//    var observableCalled = 0
//
//    observableOf("One", "Two")
//        .flatMap {
//          flatMapItems.add(it)
//          observableReturning { ++observableCalled }
//        }
//        .testObservable {
//          item(1)
//          item(2)
//          complete()
//        }
//  }
//
//  @Test fun flatMapManyEmpty() = runTest {
//    emptyMany<String>()
//        .flatMap { failMany<String>() }
//        .testObservable {
//          complete()
//        }
//  }
//
//  @Test fun flatMapManyError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toMany<String>()
//        .flatMap { failMany<String>() }
//        .testObservable {
//          error(exception)
//        }
//  }
//
//  @Test fun flatMapMaybe() = runTest {
//    val flatMapItems = mutableListOf<String>()
//    var maybeCalled = 0
//
//    observableOf("One", "Two")
//        .flatMap {
//          flatMapItems.add(it)
//          maybeReturning { ++maybeCalled }
//        }
//        .testObservable {
//          item(1)
//          item(2)
//          complete()
//        }
//
//    assertEquals(listOf("One", "Two"), flatMapItems)
//    assertEquals(2, maybeCalled)
//  }
//
//  @Test fun flatMapMaybeEmpty() = runTest {
//    emptyMany<String>()
//        .flatMap { failMaybe<String>() }
//        .testObservable {
//          complete()
//        }
//  }
//
//  @Test fun flatMapMaybeError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toMany<String>()
//        .flatMap { failMaybe<String>() }
//        .testObservable {
//          error(exception)
//        }
//  }
//
//  @Test fun flatMapOne() = runTest {
//    val flatMapItems = mutableListOf<String>()
//    var oneCalled = 0
//
//    observableOf("One", "Two")
//        .flatMap {
//          flatMapItems.add(it)
//          observableReturning { ++oneCalled }
//        }
//        .testObservable {
//          item(1)
//          item(2)
//          complete()
//        }
//
//    assertEquals(listOf("One", "Two"), flatMapItems)
//    assertEquals(2, oneCalled)
//  }
//
//  @Test fun flatMapOneEmpty() = runTest {
//    emptyMany<String>()
//        .flatMap { failOne<String>() }
//        .testObservable {
//          complete()
//        }
//  }
//
//  @Test fun flatMapOneError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toMany<String>()
//        .flatMap { failOne<String>() }
//        .testObservable {
//          error(exception)
//        }
//  }

  @Test fun flatMapTask() = runTest {
    val flatMapItems = mutableListOf<String>()
    var taskCalled = 0

    observableOf("One", "Two")
        .flatMap {
          flatMapItems.add(it)
          observableRunning { ++taskCalled }
        }
        .testTask {
          complete()
        }

    assertEquals(listOf("One", "Two"), flatMapItems)
    assertEquals(2, taskCalled)
  }

  @Test fun flatMapTaskEmpty() = runTest {
    emptyMany<String>()
        .flatMap { failTask() }
        .testTask {
          complete()
        }
  }

  @Test fun flatMapTaskError() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toMany<String>()
        .flatMap { failTask() }
        .testTask {
          error(exception)
        }
  }
}
