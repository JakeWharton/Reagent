/*
 * Copyright 2017 Google, Inc.
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

import kotlinx.coroutines.experimental.await
import reagent.tester.testOne
import kotlin.js.Promise
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.fail

class OneKtTest {
  @Test fun toOneResolve() = runTest {
    Promise.resolve("Hello")
        .toOne()
        .testOne {
          item("Hello")
        }
  }

  @Test fun toOneReject() = runTest {
    val exception = RuntimeException("Hello")
    Promise.reject(exception)
        .toOne()
        .testOne {
          error(exception)
        }
  }

  @Test fun itemToPromise() = runTest {
    val value = One.just("Hello")
        .toPromise()
        .await()
    assertEquals("Hello", value)
  }

  @Test fun errorToPromise() = runTest {
    val exception = RuntimeException("Hello")
    val promise = One.error<String>(exception)
        .toPromise()
    try {
      promise.await()
      fail()
    } catch (t: Throwable) {
      assertSame(exception, t)
    }
  }
}
