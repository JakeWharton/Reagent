package reagent

import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicBoolean
import reagent.tester.testTask

class TaskKtTest {
  @Test
  fun fromRunnable() {
    val called = AtomicBoolean()
    Runnable { called.set(true) }
        .asTask()
        .testTask {
          complete()
        }
    assertTrue(called.get())
  }

  @Test
  fun fromRunnableThrowing() {
    val exception = RuntimeException("Oops!")
    Runnable { throw exception }
        .asTask()
        .testTask {
          error(exception)
        }
  }

  @Test fun fromCallable() {
    val called = AtomicBoolean()
    Callable { called.set(true) }
        .asTask()
        .testTask {
          complete()
        }
    assertTrue(called.get())
  }

  @Test fun fromCallableThrowing() {
    val exception = RuntimeException("Oops!")
    Callable { throw exception }
        .asTask()
        .testTask {
          error(exception)
        }
  }
}
