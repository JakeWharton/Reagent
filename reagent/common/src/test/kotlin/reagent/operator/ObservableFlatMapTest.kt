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

class ObservableFlatMapTest {
// TODO overload resolution doesn't work here
//  @Test fun flatMapObservable() = runTest {
//    val flatMapItems = mutableListOf<String>()
//    var observableCalled = 0
//
//    observableOf("Task", "Two")
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
//  @Test fun flatMapObservableEmpty() = runTest {
//    emptyActualObservable<String>()
//        .flatMap { failObservable<String>() }
//        .testObservable {
//          complete()
//        }
//  }
//
//  @Test fun flatMapObservableError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toActualObservable<String>()
//        .flatMap { failObservable<String>() }
//        .testObservable {
//          error(exception)
//        }
//  }
//
//  @Test fun flatMapOne() = runTest {
//    val flatMapItems = mutableListOf<String>()
//    var oneCalled = 0
//
//    observableOf("Task", "Two")
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
//    assertEquals(listOf("Task", "Two"), flatMapItems)
//    assertEquals(2, oneCalled)
//  }
//
//  @Test fun flatMapOneEmpty() = runTest {
//    emptyActualObservable<String>()
//        .flatMap { failOne<String>() }
//        .testObservable {
//          complete()
//        }
//  }
//
//  @Test fun flatMapOneError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toActualObservable<String>()
//        .flatMap { failOne<String>() }
//        .testObservable {
//          error(exception)
//        }
//  }
}
