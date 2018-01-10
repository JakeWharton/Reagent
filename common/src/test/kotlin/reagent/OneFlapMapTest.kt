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
//class OneFlapMapTest {
//  @Test fun flatMapMany() {
//    PureOne.just("Item")
//        .flatMap { Many.fromArray("Hello", "World") }
//        .testMany {
//          item("Hello")
//          item("World")
//          complete()
//        }
//  }
//
//  @Test fun flatMapManyError() {
//    val exception = RuntimeException("Oops!")
//    PureOne.error(exception)
//        .flatMap { Many.fromArray("Hello", "World") }
//        .testMany {
//          error(exception)
//        }
//  }
//
//  @Test fun flatMapMaybe() {
//    PureOne.just("Item")
//        .flatMap { Maybe.just("Hello") }
//        .testMaybe {
//          item("Hello")
//        }
//  }
//
//  @Test fun flatMapMaybeError() {
//    val exception = RuntimeException("Oops!")
//    PureOne.error(exception)
//        .flatMap { Maybe.just("Hello") }
//        .testMaybe {
//          error(exception)
//        }
//  }
//
//  @Test fun flatMapOne() {
//    PureOne.just("Item")
//        .flatMap { One.just("Hello") }
//        .testOne {
//          item("Hello")
//        }
//  }
//
//  @Test fun flatMapOneError() {
//    val exception = RuntimeException("Oops!")
//    PureOne.error(exception)
//        .flatMap { One.just("Hello") }
//        .testOne {
//          error(exception)
//        }
//  }
//
//  @Test fun flatMapTask() {
//    One.just("Item")
//        .flatMap { Task.empty() }
//        .testTask {
//          complete()
//        }
//  }
//
//  @Test fun flatMapTaskError() {
//    val exception = RuntimeException("Oops!")
//    One.error<String>(exception)
//        .flatMap { Task.empty() }
//        .testTask {
//          error(exception)
//        }
//  }
//}
