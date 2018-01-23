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
package reagent.source

import kotlinx.coroutines.experimental.delay
import reagent.Observable
import reagent.runTest
import reagent.tester.testObservable
import kotlin.test.Test
import kotlin.test.assertEquals

class ObservableSourceTest {
  @Test fun suspendingLambda() = runTest {
    observable<String> { emit ->
      delay(10)
      emit("Hello")
    }.testObservable {
      item("Hello")
      complete()
    }
  }

  @Test fun ofSingle() = runTest {
    observableOf("Hello")
        .testObservable {
          item("Hello")
          complete()
        }
  }

  @Test fun ofMultiple() = runTest {
    observableOf("Hello", "World")
        .testObservable {
          item("Hello")
          item("World")
          complete()
        }
  }

  @Test fun ofEmpty() = runTest {
    observableOf()
        .testObservable {
          complete()
        }
  }

  @Test fun array() = runTest {
    arrayOf("Hello", "World")
        .toObservable()
        .testObservable {
          item("Hello")
          item("World")
          complete()
        }
  }

  @Test fun iterable() = runTest {
    listOf("Hello", "World")
        .toObservable()
        .testObservable {
          item("Hello")
          item("World")
          complete()
        }
  }

  @Test fun sequence() = runTest {
    sequenceOf(1, 2, 4, 8)
        .toObservable()
        .testObservable {
          item(1)
          item(2)
          item(4)
          item(8)
          complete()
        }
  }

  @Test fun defer() = runTest {
    var called = 0
    val deferred = deferObservable { called++; observableOf("Hello") }
    deferred.testObservable {
      item("Hello")
      complete()
    }
    assertEquals(1, called)
    deferred.testObservable {
      item("Hello")
      complete()
    }
    assertEquals(2, called)
  }

  @Test fun concatEmpty() = runTest {
    concat(emptyList<Observable<Nothing>>())
        .testObservable {
          complete()
        }
  }

  @Test fun concat() = runTest {
    concat(
        observableOf(1),
        observableOf(2),
        observableOf(3),
        observableOf(4)
    ).testObservable {
      item(1)
      item(2)
      item(3)
      item(4)
      complete()
    }
  }
}
