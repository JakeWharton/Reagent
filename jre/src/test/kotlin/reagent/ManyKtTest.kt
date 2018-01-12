package reagent

import org.junit.Assert.assertTrue
import org.junit.Test
import reagent.tester.testMany
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicBoolean

class ManyKtTest {
  @Test fun fromCallable() = runTest {
    val called = AtomicBoolean()
    Callable { called.getAndSet(true) }
        .asMany()
        .testMany {
          item(false)
          complete()
        }
    assertTrue(called.get())
  }

  @Test fun fromCallableThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    Callable { throw exception }
        .asMany()
        .testMany {
          error(exception)
        }
  }

  @Test
  fun fromRunnable() = runTest {
    val called = AtomicBoolean()
    Runnable { called.set(true) }
        .asMany<Any>()
        .testMany {
          complete()
        }
    assertTrue(called.get())
  }

  @Test
  fun fromRunnableThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    Runnable { throw exception }
        .asMany<Any>()
        .testMany {
          error(exception)
        }
  }
}
