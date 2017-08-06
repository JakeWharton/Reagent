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

// TODO overload resolution doesn't work here
// TODO @Ignore("Not implemented")
//class ManyFlatMapTest {
//  @Test fun flatMapMany() {
//    val flatMapItems = mutableListOf<String>()
//    var manyCalled = 0
//
//    PureMany.just("One", "Two")
//        .flatMapMany {
//          flatMapItems.add(it)
//          Many.returning { ++manyCalled }
//        }
//        .testMany {
//          item(1)
//          item(2)
//          complete()
//        }
//  }
//
//  @Test fun flatMapManyEmpty() {
//    PureMany.empty()
//        .flatMapMany<Any> { throw AssertionError() }
//        .testMany {
//          complete()
//        }
//  }
//
//  @Test fun flatMapManyError() {
//    val exception = RuntimeException("Oops!")
//    PureMany.error(exception)
//        .flatMapMany<Any> { throw AssertionError() }
//        .testMany {
//          error(exception)
//        }
//  }
//
//  @Test fun flatMapMaybe() {
//    val flatMapItems = mutableListOf<String>()
//    var maybeCalled = 0
//
//    PureMany.just("One", "Two")
//        .flatMapMaybe {
//          flatMapItems.add(it)
//          Maybe.returning { ++maybeCalled }
//        }
//        .testMany {
//          item(1)
//          item(2)
//          complete()
//        }
//
//    assertEquals(listOf("One", "Two"), flatMapItems)
//    assertEquals(2, maybeCalled)
//  }
//
//  @Test fun flatMapMaybeEmpty() {
//    PureMany.empty()
//        .flatMapMaybe<Any> { throw AssertionError() }
//        .testMany {
//          complete()
//        }
//  }
//
//  @Test fun flatMapMaybeError() {
//    val exception = RuntimeException("Oops!")
//    PureMany.error(exception)
//        .flatMapMaybe<Any> { throw AssertionError() }
//        .testMany {
//          error(exception)
//        }
//  }
//
//  @Test fun flatMapOne() {
//    val flatMapItems = mutableListOf<String>()
//    var oneCalled = 0
//
//    PureMany.just("One", "Two")
//        .flatMapOne {
//          flatMapItems.add(it)
//          One.returning { ++oneCalled }
//        }
//        .testMany {
//          item(1)
//          item(2)
//          complete()
//        }
//
//    assertEquals(listOf("One", "Two"), flatMapItems)
//    assertEquals(2, oneCalled)
//  }
//
//  @Test fun flatMapOneEmpty() {
//    PureMany.empty()
//        .flatMapOne<Any> { throw AssertionError() }
//        .testMany {
//          complete()
//        }
//  }
//
//  @Test fun flatMapOneError() {
//    val exception = RuntimeException("Oops!")
//    PureMany.error(exception)
//        .flatMapOne<Any> { throw AssertionError() }
//        .testMany {
//          error(exception)
//        }
//  }
//
//  @Test fun flatMapTask() {
//    val flatMapItems = mutableListOf<String>()
//    var taskCalled = 0
//
//    PureMany.just("One", "Two")
//        .flatMapTask {
//          flatMapItems.add(it)
//          Task.running { ++taskCalled }
//        }
//        .testTask {
//          complete()
//        }
//
//    assertEquals(listOf("One", "Two"), flatMapItems)
//    assertEquals(2, taskCalled)
//  }
//
//  @Test fun flatMapTaskEmpty() {
//    PureMany.empty()
//        .flatMapTask { throw AssertionError() }
//        .testTask {
//          complete()
//        }
//  }
//
//  @Test fun flatMapTaskError() {
//    val exception = RuntimeException("Oops!")
//    PureMany.error(exception)
//        .flatMapTask { throw AssertionError() }
//        .testTask {
//          complete()
//        }
//  }
//}
