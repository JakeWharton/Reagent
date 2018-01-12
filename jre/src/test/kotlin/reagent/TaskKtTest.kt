package reagent

import org.junit.Assert.assertTrue
import org.junit.Test
import reagent.tester.testTask
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicBoolean

class TaskKtTest {
  @Test
  fun fromRunnable() = runTest {
    val called = AtomicBoolean()
    Runnable { called.set(true) }
        .asTask()
        .testTask {
          complete()
        }
    assertTrue(called.get())
  }

  @Test
  fun fromRunnableThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    Runnable { throw exception }
        .asTask()
        .testTask {
          error(exception)
        }
  }

  @Test fun fromCallable() = runTest {
    val called = AtomicBoolean()
    Callable { called.set(true) }
        .asTask()
        .testTask {
          complete()
        }
    assertTrue(called.get())
  }

  @Test fun fromCallableThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    Callable { throw exception }
        .asTask()
        .testTask {
          error(exception)
        }
  }
}
