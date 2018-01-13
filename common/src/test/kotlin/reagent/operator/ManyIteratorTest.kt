package reagent.operator

import reagent.Emitter
import reagent.Many
import reagent.runTest
import reagent.source.emptyMany
import reagent.source.manyOf
import reagent.source.toMany
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue
import kotlin.test.fail

@Ignore
class ManyIteratorTest {
  @Test fun single() = runTest {
    val items = mutableListOf<String>()
    for (item in manyOf("Hello")) {
      items.add(item)
    }
    assertEquals(listOf("Hello"), items)
  }

  @Test fun multiple() = runTest {
    val items = mutableListOf<String>()
    for (item in manyOf("Hello", "World")) {
      items.add(item)
    }
    assertEquals(listOf("Hello", "World"), items)
  }

  @Test fun empty() = runTest {
    for (item in emptyMany<String>()) {
      fail()
    }
  }

  @Test fun error() = runTest {
    val exception = RuntimeException()
    try {
      for (item in exception.toMany<String>()) {
        fail()
      }
      fail()
    } catch (actual: Throwable) {
      assertSame(exception, actual)
    }
  }

  @Test fun iteratorContract() = runTest {
    var called = 0
    val task = object : Many<String>() {
      override suspend fun subscribe(emit: Emitter<String>) {
        called++
        emit("Hello")
        emit("World")
      }
    }

    val iterator = task.iterator()
    assertEquals(0, called)

    assertTrue(iterator.hasNext())
    assertEquals("Hello", iterator.next())
    assertEquals(1, called)

    assertTrue(iterator.hasNext())
    assertEquals("World", iterator.next())

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
    val task = object : Many<String>() {
      override suspend fun subscribe(emit: Emitter<String>) {
        called++
        emit("Hello")
        emit("World")
      }
    }

    val iterator = task.iterator()
    assertEquals(0, called)

    assertEquals("Hello", iterator.next())
    assertEquals(1, called)

    assertEquals("World", iterator.next())

    try {
      iterator.next()
    } catch (e: IllegalStateException) {
      assertEquals("Must call hasNext() before next()", e.message)
    }
    assertEquals(1, called)
  }
}
