package reagent.operator

import reagent.runTest
import reagent.source.test.emptyMaybe
import reagent.source.test.maybeOf
import reagent.source.test.toMaybe
import reagent.tester.testOne
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class MaybeItemTest {
  @Test fun nothingItem() = runTest {
    emptyMaybe<Nothing>()
        .item()
        .testOne {
          error {
            assertTrue(it is NoSuchElementException)
            assertEquals("Maybe was empty.", it.message)
          }
        }
  }

  @Test fun itemItem() = runTest {
    maybeOf(Unit)
        .item()
        .testOne {
          item(Unit)
        }
  }

  @Test fun errorItem() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toMaybe<Nothing>()
        .item()
        .testOne {
          error(exception)
        }
  }

  @Test fun nothingOrElse() = runTest {
    emptyMaybe<Nothing>()
        .itemOrElse(Unit)
        .testOne {
          item(Unit)
        }
  }

  @Test fun itemOrElse() = runTest {
    maybeOf(Unit)
        .itemOrElse(Any())
        .testOne {
          item(Unit)
        }
  }

  @Test fun errorOrElse() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toMaybe<Nothing>()
        .itemOrElse(Any())
        .testOne {
          error(exception)
        }
  }

  @Test fun nothingOrElseSupplier() = runTest {
    var called = 0
    val one = emptyMaybe<Nothing>().itemOrElse { called++ }
    one.testOne { item(0) }
    one.testOne { item(1) }
  }

  @Test fun itemOrElseSupplier() = runTest {
    maybeOf(Unit)
        .itemOrElse { fail() }
        .testOne {
          item(Unit)
        }
  }

  @Test fun errorOrElseSupplier() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toMaybe<Nothing>()
        .itemOrElse { fail() }
        .testOne {
          error(exception)
        }
  }
}
