package reagent.operator

import reagent.Observable
import reagent.runTest
import reagent.source.emptyObservable
import reagent.source.observableOf
import reagent.tester.testTask
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class ObservableReduceTest {
  @Test fun empty() = runTest {
    emptyObservable()
        .reduce { _, _ -> fail() }
        .testTask {
          error {
            assertTrue(it is NoSuchElementException)
            assertEquals("Reduce requires a non-empty Observable", it.message)
          }
        }
  }

  @Test fun one() = runTest {
    (observableOf(1) as Observable<Int>)
        .reduce { _, _ -> fail() }
        .testTask {
          item(1)
        }
  }

  @Test fun multiple() = runTest {
    observableOf(1, 2, 3)
        .reduce { accumulator, item -> accumulator + item }
        .testTask {
          item(6)
        }
  }
}
