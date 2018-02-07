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

class ObservableConcatMapTest {
// TODO overload resolution doesn't work here
//  @Test fun concatMapObservable() = runTest {
//    val concatMapItems = mutableListOf<String>()
//    var observableCalled = 0
//
//    observableOf("Task", "Two")
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
//  @Test fun concatMapObservableEmpty() = runTest {
//    emptyActualObservable<String>()
//        .concatMap { failObservable<String>() }
//        .testObservable {
//          complete()
//        }
//  }
//
//  @Test fun concatMapObservableError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toActualObservable<String>()
//        .concatMap { failObservable<String>() }
//        .testObservable {
//          error(exception)
//        }
//  }
//
//  @Test fun concatMapOne() = runTest {
//    val concatMapItems = mutableListOf<String>()
//    var oneCalled = 0
//
//    observableOf("Task", "Two")
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
//    assertEquals(listOf("Task", "Two"), concatMapItems)
//    assertEquals(2, oneCalled)
//  }
//
//  @Test fun concatMapOneEmpty() = runTest {
//    emptyActualObservable<String>()
//        .concatMap { failOne<String>() }
//        .testObservable {
//          complete()
//        }
//  }
//
//  @Test fun concatMapOneError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toActualObservable<String>()
//        .concatMap { failOne<String>() }
//        .testObservable {
//          error(exception)
//        }
//  }
}
