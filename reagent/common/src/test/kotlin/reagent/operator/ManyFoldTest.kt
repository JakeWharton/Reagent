package reagent.operator

import reagent.runTest
import reagent.source.emptyObservable
import reagent.source.observableOf
import reagent.tester.testOne
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class ManyFoldTest {
  @Test fun empty() = runTest {
    emptyObservable()
        .fold("Hello") { _, _ -> fail() }
        .testOne {
          item("Hello")
        }
  }

  @Test fun one() = runTest {
    observableOf("Item")
        .fold("Seed") { accumulator, item ->
          assertEquals("Seed", accumulator)
          assertEquals("Item", item)
          "Return"
        }.testOne {
          item("Return")
        }
  }

  @Test fun multiple() = runTest {
    observableOf(1, 2, 3)
        .fold(0) { accumulator, item -> accumulator + item }
        .testOne {
          item(6)
        }
  }
}
