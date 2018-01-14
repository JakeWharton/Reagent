package reagent.operator

import reagent.runTest
import reagent.source.emptyMany
import reagent.source.manyOf
import reagent.tester.testOne
import kotlin.test.Test
import kotlin.test.fail

class ManyAllTest {
  @Test fun empty() = runTest {
    emptyMany<Any>()
        .all { fail() }
        .testOne {
          item(true)
        }
  }

  @Test fun success() = runTest {
    manyOf(1, 2, 3)
        .all { true }
        .testOne {
          item(true)
        }
  }

  @Test fun failure() = runTest {
    manyOf(1, 2, 3)
        .all { it % 2 == 1 }
        .testOne {
          item(false)
        }
  }
}
