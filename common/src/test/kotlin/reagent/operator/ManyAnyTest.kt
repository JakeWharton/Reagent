package reagent.operator

import reagent.runTest
import reagent.source.emptyMany
import reagent.source.manyOf
import reagent.tester.testOne
import kotlin.test.Test
import kotlin.test.fail

class ManyAnyTest {
  @Test fun empty() = runTest {
    emptyMany<Any>()
        .any { fail() }
        .testOne {
          item(false)
        }
  }

  @Test fun success() = runTest {
    manyOf(1, 2, 3)
        .any { it == 2 }
        .testOne {
          item(true)
        }
  }

  @Test fun failure() = runTest {
    manyOf(1, 2, 3)
        .all { false }
        .testOne {
          item(false)
        }
  }
}
