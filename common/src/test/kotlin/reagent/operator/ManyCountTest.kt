package reagent.operator

import reagent.runTest
import reagent.source.emptyMany
import reagent.source.manyOf
import reagent.tester.testOne
import kotlin.test.Test
import kotlin.test.fail

class ManyCountTest {
  @Test fun empty() = runTest {
    emptyMany<Any>()
        .count()
        .testOne {
          item(0)
        }
  }

  @Test fun nonEmpty() = runTest {
    manyOf(1, 2, 3)
        .count()
        .testOne {
          item(3)
        }
  }
}
