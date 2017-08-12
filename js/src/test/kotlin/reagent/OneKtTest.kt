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

import reagent.One
import reagent.tester.testOne
import kotlin.js.Promise

class OneKtTest {
  // TODO @Test: Requires blocking for One result.
  fun toOneResolve() {
    Promise.resolve("Hello")
        .toOne()
        .testOne {
          item("Hello")
        }
  }

  // TODO @Test: Requires blocking for One result.
  fun toOneReject() {
    val exception = RuntimeException("Hello")
    Promise.reject(exception)
        .toOne()
        .testOne {
          error(exception)
        }
  }

  // TODO @Test: Requires blocking for promise result.
  fun itemToPromise() {
    One.just("Hello")
        .toPromise()
        //something?
  }

  // TODO @Test: Requires blocking for promise result.
  fun errorToPromise() {
    val exception = RuntimeException("Hello")
    One.error<String>(exception)
        .toPromise()
        //something?
  }
}
