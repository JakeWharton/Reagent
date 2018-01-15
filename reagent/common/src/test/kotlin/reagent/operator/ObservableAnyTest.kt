package reagent.operator

import reagent.runTest
import reagent.source.observableOf
import reagent.source.test.emptyMany
import reagent.tester.testOne
import kotlin.test.Test
import kotlin.test.fail

class ObservableAnyTest {
  @Test fun empty() = runTest {
    emptyMany<Any>()
        .any { fail() }
        .testOne {
          item(false)
        }
  }

  @Test fun success() = runTest {
    observableOf(1, 2, 3)
        .any { it == 2 }
        .testOne {
          item(true)
        }
  }

  @Test fun failure() = runTest {
    observableOf(1, 2, 3)
        .all { false }
        .testOne {
          item(false)
        }
  }
}
