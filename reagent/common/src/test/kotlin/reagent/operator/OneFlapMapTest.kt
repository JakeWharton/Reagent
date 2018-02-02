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

import kotlin.test.Ignore

// TODO overload resolution doesn't work here
@Ignore
class OneFlapMapTest {
//  @Test fun flatMapObservable() = runTest {
//    oneOf("Item")
//        .flatMap { observableOf("Hello", "World") }
//        .testObservable {
//          item("Hello")
//          item("World")
//          complete()
//        }
//  }
//
//  @Test fun flatMapObservableError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toOne<String>()
//        .flatMap { failObservable<String>() }
//        .testObservable {
//          error(exception)
//        }
//  }
//
//  @Test fun flatMapOne() = runTest {
//    oneOf("Item")
//        .flatMap { oneOf("Hello") }
//        .testOne {
//          item("Hello")
//        }
//  }
//
//  @Test fun flatMapOneError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toOne<String>()
//        .flatMap { failOne<String>() }
//        .testOne {
//          error(exception)
//        }
//  }
//
//  @Test fun flatMapTask() = runTest {
//    oneOf("Item")
//        .flatMap { emptyObservable() }
//        .testTask {
//          complete()
//        }
//  }
//
//  @Test fun flatMapTaskError() = runTest {
//    val exception = RuntimeException("Oops!")
//    exception.toOne<String>()
//        .flatMap { failTask() }
//        .testTask {
//          error(exception)
//        }
//  }
}
