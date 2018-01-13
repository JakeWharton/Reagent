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

import reagent.operator.concatMap
import reagent.source.emptyMany
import reagent.source.failTask
import reagent.source.manyOf
import reagent.source.taskRunning
import reagent.source.toMany
import reagent.tester.testTask
import kotlin.test.Test
import kotlin.test.assertEquals

class ManyConcatMapTest {
// TODO overload resolution doesn't work here
//  @Test fun concatMapMany() = runTest {
//    val concatMapItems = mutableListOf<String>()
//    var manyCalled = 0
//
//    manyOf("One", "Two")
//        .concatMap {
//          concatMapItems.add(it)
//          manyReturning { ++manyCalled }
//        }
//        .testMany {
//          item(1)
//          item(2)
//          complete()
//        }
//  }
//
//  @Test fun concatMapManyEmpty() = runTest {
//    emptyMany<String>()
//        .concatMap { failMany<String>() }
//        .testMany {
//          complete()
//        }
//  }
//
//  @Test fun concatMapManyError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toMany<String>()
//        .concatMap { failMany<String>() }
//        .testMany {
//          error(exception)
//        }
//  }
//
//  @Test fun concatMapMaybe() = runTest {
//    val concatMapItems = mutableListOf<String>()
//    var maybeCalled = 0
//
//    manyOf("One", "Two")
//        .concatMap {
//          concatMapItems.add(it)
//          maybeReturning { ++maybeCalled }
//        }
//        .testMany {
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
//        .testMany {
//          complete()
//        }
//  }
//
//  @Test fun concatMapMaybeError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toMany<String>()
//        .concatMap { failMaybe<String>() }
//        .testMany {
//          error(exception)
//        }
//  }
//
//  @Test fun concatMapOne() = runTest {
//    val concatMapItems = mutableListOf<String>()
//    var oneCalled = 0
//
//    manyOf("One", "Two")
//        .concatMap {
//          concatMapItems.add(it)
//          oneReturning { ++oneCalled }
//        }
//        .testMany {
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
//        .testMany {
//          complete()
//        }
//  }
//
//  @Test fun concatMapOneError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toMany<String>()
//        .concatMap { failOne<String>() }
//        .testMany {
//          error(exception)
//        }
//  }

  @Test fun concatMapTask() = runTest {
    val concatMapItems = mutableListOf<String>()
    var taskCalled = 0

    manyOf("One", "Two")
        .concatMap {
          concatMapItems.add(it)
          taskRunning { ++taskCalled }
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
