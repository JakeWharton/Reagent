package reagent.operator

import reagent.One
import reagent.runTest
import reagent.source.oneOf
import reagent.source.toOne
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue
import kotlin.test.fail

class OneIteratorTest {
  @Test fun item() = runTest {
    val items = mutableListOf<String>()
    for (item in oneOf("Hello")) {
      items.add(item)
    }
    assertEquals(listOf("Hello"), items)
  }

  @Test fun error() = runTest {
    val exception = RuntimeException()
    try {
      for (item in exception.toOne<String>()) {
        fail()
      }
      fail()
    } catch (actual: Throwable) {
      assertSame(exception, actual)
    }
  }

  @Test fun iteratorContract() = runTest {
    var called = 0
    val task = object : One<String>() {
      override suspend fun produce(): String {
        called++
        return "Hello"
      }
    }

    val iterator = task.iterator()
    assertEquals(0, called)

    assertTrue(iterator.hasNext())
    assertEquals("Hello", iterator.next())
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
    val task = object : One<String>() {
      override suspend fun produce(): String {
        called++
        return "Hello"
      }
    }

    val iterator = task.iterator()
    assertEquals(0, called)

    assertEquals("Hello", iterator.next())
    assertEquals(1, called)

    try {
      iterator.next()
    } catch (e: IllegalStateException) {
      assertEquals("Must call hasNext() before next()", e.message)
    }
    assertEquals(1, called)
  }
}
