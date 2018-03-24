package reagent.operator

import reagent.runTest
import reagent.source.emptyObservable
import reagent.source.observableOf
import reagent.tester.testTask
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class ObservableMinMaxTest {
  @Test fun emptyMax() = runTest {
    emptyObservable()
        .max()
        .testTask {
          error {
            assertTrue(it is NoSuchElementException)
            assertEquals("No elements to compare", it.message)
          }
        }
  }

  @Test fun emptyMaxBy() = runTest {
    emptyObservable()
        .maxBy { fail() }
        .testTask {
          error {
            assertTrue(it is NoSuchElementException)
            assertEquals("No elements to compare", it.message)
          }
        }
  }

  @Test fun emptyMin() = runTest {
    emptyObservable()
        .min()
        .testTask {
          error {
            assertTrue(it is NoSuchElementException)
            assertEquals("No elements to compare", it.message)
          }
        }
  }

  @Test fun emptyMinBy() = runTest {
    emptyObservable()
        .minBy { fail() }
        .testTask {
          error {
            assertTrue(it is NoSuchElementException)
            assertEquals("No elements to compare", it.message)
          }
        }
  }

  @Test fun maxOne() = runTest {
    observableOf(1)
        .max()
        .testTask {
          item(1)
        }
  }

  @Test fun maxMany() = runTest {
    observableOf(1, 5, 2, 3, 4)
        .max()
        .testTask {
          item(5)
        }
  }

  @Test fun maxByOne() = runTest {
    observableOf(1)
        .maxBy { fail() }
        .testTask {
          item(1)
        }
  }

  @Test fun maxByMany() = runTest {
    observableOf("Hello", "World", "Test")
        .maxBy { it[0] }
        .testTask {
          item("World")
        }
  }

  @Test fun minOne() = runTest {
    observableOf(1)
        .min()
        .testTask {
          item(1)
        }
  }

  @Test fun minMany() = runTest {
    observableOf(5, 1, 4, 2, 3)
        .min()
        .testTask {
          item(1)
        }
  }

  @Test fun minByOne() = runTest {
    observableOf(1)
        .minBy { fail() }
        .testTask {
          item(1)
        }
  }

  @Test fun minByMany() = runTest {
    observableOf("Test", "Hello", "World")
        .minBy { it[0] }
        .testTask {
          item("Hello")
        }
  }
}
