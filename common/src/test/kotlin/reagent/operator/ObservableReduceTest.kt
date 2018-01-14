package reagent.operator

import reagent.Observable
import reagent.runTest
import reagent.source.emptyObservable
import reagent.source.observableOf
import reagent.tester.testOne
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class ObservableReduceTest {
  @Test fun empty() = runTest {
    (emptyObservable() as Observable<Nothing>)
        .reduce { _, _ -> fail() }
        .testOne {
          error {
            assertTrue(it is NoSuchElementException)
            assertEquals("Reduce requires a non-empty Observable", it.message)
          }
        }
  }

  @Test fun one() = runTest {
    (observableOf(1) as Observable<Int>)
        .reduce { _, _ -> fail() }
        .testOne {
          item(1)
        }
  }

  @Test fun multiple() = runTest {
    observableOf(1, 2, 3)
        .reduce { accumulator, item -> accumulator + item }
        .testOne {
          item(6)
        }
  }
}
