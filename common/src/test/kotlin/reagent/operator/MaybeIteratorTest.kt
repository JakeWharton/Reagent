package reagent.operator

import reagent.Maybe
import reagent.runTest
import reagent.source.test.emptyMaybe
import reagent.source.test.maybeOf
import reagent.source.test.toMaybe
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue
import kotlin.test.fail

class MaybeIteratorTest {
  @Test fun item() = runTest {
    val items = mutableListOf<String>()
    for (item in maybeOf("Hello")) {
      items.add(item)
    }
    assertEquals(listOf("Hello"), items)
  }

  @Test fun nothing() = runTest {
    for (item in emptyMaybe<String>()) {
      fail()
    }
  }

  @Test fun error() = runTest {
    val exception = RuntimeException()
    try {
      for (item in exception.toMaybe<String>()) {
        fail()
      }
      fail()
    } catch (actual: Throwable) {
      assertSame(exception, actual)
    }
  }

  @Test fun iteratorContract() = runTest {
    var called = 0
    val task = object : Maybe<String>() {
      override suspend fun produce(): String? {
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

  @Test fun iteratorNextOnly() = runTest {
    var called = 0
    val task = object : Maybe<String>() {
      override suspend fun produce(): String? {
        called++
        return "Hello"
      }
    }

    val iterator = task.iterator()
    assertEquals(0, called)

    assertEquals("Hello", iterator.next())
    try {
      iterator.next()
    } catch (e: IllegalStateException) {
      assertEquals("Must call hasNext() before next()", e.message)
    }
    assertEquals(1, called)
  }
}
