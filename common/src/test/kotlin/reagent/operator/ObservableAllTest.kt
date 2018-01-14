package reagent.operator

import reagent.runTest
import reagent.source.observableOf
import reagent.source.test.emptyMany
import reagent.tester.testOne
import kotlin.test.Test
import kotlin.test.fail

class ObservableAllTest {
  @Test fun empty() = runTest {
    emptyMany<Any>()
        .all { fail() }
        .testOne {
          item(true)
        }
  }

  @Test fun success() = runTest {
    observableOf(1, 2, 3)
        .all { true }
        .testOne {
          item(true)
        }
  }

  @Test fun failure() = runTest {
    observableOf(1, 2, 3)
        .all { it % 2 == 1 }
        .testOne {
          item(false)
        }
  }
}
