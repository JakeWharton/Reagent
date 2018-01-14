package reagent.operator

import reagent.runTest
import reagent.source.emptyObservable
import reagent.source.toTask
import reagent.tester.testOne
import kotlin.test.Test
import kotlin.test.fail

class TaskThenReturnTest {
  @Test fun nothingThenReturnUnit() = runTest {
    emptyObservable()
        .thenReturnUnit()
        .testOne {
          item(Unit)
        }
  }

  @Test fun errorThenReturnUnit() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toTask()
        .thenReturnUnit()
        .testOne {
          error(exception)
        }
  }

  @Test fun nothingThenReturn() = runTest {
    val value = Any()
    emptyObservable()
        .thenReturn(value)
        .testOne {
          item(value)
        }
  }

  @Test fun errorThenReturn() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toTask()
        .thenReturn(Any())
        .testOne {
          error(exception)
        }
  }

  @Test fun nothingThenReturnSupplier() = runTest {
    var called = 0
    val one = emptyObservable().thenReturn { called++ }
    one.testOne { item(0) }
    one.testOne { item(1) }
  }

  @Test fun errorThenReturnSupplier() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toTask()
        .thenReturn { fail() }
        .testOne {
          error(exception)
        }
  }
}
