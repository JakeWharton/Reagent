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

class ObservableConcatMapTest {
// TODO overload resolution doesn't work here
//  @Test fun concatMapMany() = runTest {
//    val concatMapItems = mutableListOf<String>()
//    var observableCalled = 0
//
//    observableOf("One", "Two")
//        .concatMap {
//          concatMapItems.add(it)
//          observableReturning { ++observableCalled }
//        }
//        .testObservable {
//          item(1)
//          item(2)
//          complete()
//        }
//  }
//
//  @Test fun concatMapManyEmpty() = runTest {
//    emptyMany<String>()
//        .concatMap { failMany<String>() }
//        .testObservable {
//          complete()
//        }
//  }
//
//  @Test fun concatMapManyError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toMany<String>()
//        .concatMap { failMany<String>() }
//        .testObservable {
//          error(exception)
//        }
//  }
//
//  @Test fun concatMapMaybe() = runTest {
//    val concatMapItems = mutableListOf<String>()
//    var maybeCalled = 0
//
//    observableOf("One", "Two")
//        .concatMap {
//          concatMapItems.add(it)
//          maybeReturning { ++maybeCalled }
//        }
//        .testObservable {
//          item(1)
//          item(2)
//          complete()
//        }
//
//    assertEquals(listOf("One", "Two"), concatMapItems)
//    assertEquals(2, maybeCalled)
//  }
//
//  @Test fun concatMapMaybeEmpty() = runTest {
//    emptyMany<String>()
//        .concatMap { failMaybe<String>() }
//        .testObservable {
//          complete()
//        }
//  }
//
//  @Test fun concatMapMaybeError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toMany<String>()
//        .concatMap { failMaybe<String>() }
//        .testObservable {
//          error(exception)
//        }
//  }
//
//  @Test fun concatMapOne() = runTest {
//    val concatMapItems = mutableListOf<String>()
//    var oneCalled = 0
//
//    observableOf("One", "Two")
//        .concatMap {
//          concatMapItems.add(it)
//          observableReturning { ++oneCalled }
//        }
//        .testObservable {
//          item(1)
//          item(2)
//          complete()
//        }
//
//    assertEquals(listOf("One", "Two"), concatMapItems)
//    assertEquals(2, oneCalled)
//  }
//
//  @Test fun concatMapOneEmpty() = runTest {
//    emptyMany<String>()
//        .concatMap { failOne<String>() }
//        .testObservable {
//          complete()
//        }
//  }
//
//  @Test fun concatMapOneError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toMany<String>()
//        .concatMap { failOne<String>() }
//        .testObservable {
//          error(exception)
//        }
//  }

  @Test fun concatMapTask() = runTest {
    val concatMapItems = mutableListOf<String>()
    var taskCalled = 0

    observableOf("One", "Two")
        .concatMap {
          concatMapItems.add(it)
          observableRunning { ++taskCalled }
        }
        .testTask {
          complete()
        }

    assertEquals(listOf("One", "Two"), concatMapItems)
    assertEquals(2, taskCalled)
  }

  @Test fun concatMapTaskEmpty() = runTest {
    emptyMany<String>()
        .concatMap { failTask() }
        .testTask {
          complete()
        }
  }

  @Test fun concatMapTaskError() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toMany<String>()
        .concatMap { failTask() }
        .testTask {
          error(exception)
        }
  }
}
