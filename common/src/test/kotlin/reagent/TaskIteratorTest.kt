package reagent

import reagent.source.emptyTask
import reagent.source.toTask
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.fail

class TaskIteratorTest {
  @Test fun complete() = runTest {
    for (item in emptyTask()) {
      fail()
    }
  }

  @Test fun error() = runTest {
    val exception = RuntimeException()
    try {
      for (item in exception.toTask()) {
        fail()
      }
      fail()
    } catch (actual: Throwable) {
      assertSame(exception, actual)
    }
  }

  @Test fun iteratorContract() = runTest {
    var called = 0
    val task = object : Task() {
      override suspend fun run() {
        called++
      }
    }

    val iterator = task.iterator()
    assertEquals(0, called)

    assertFalse(iterator.hasNext())
    assertEquals(1, called)

    assertFalse(iterator.hasNext())
    try {
      iterator.next()
    } catch (e: IllegalStateException) {
      assertEquals("Must call hasNext() before next()", e.message)
    }
    assertEquals(1, called)
  }

  @Test fun iteratorContractNextOnly() = runTest {
    var called = 0
    val task = object : Task() {
      override suspend fun run() {
        called++
      }
    }

    val iterator = task.iterator()
    assertEquals(0, called)

    assertFalse(iterator.hasNext())
    assertEquals(1, called)

    assertFalse(iterator.hasNext())
    try {
      iterator.next()
    } catch (e: IllegalStateException) {
      assertEquals("Must call hasNext() before next()", e.message)
    }
    assertEquals(1, called)
  }
}
