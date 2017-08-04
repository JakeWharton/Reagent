package reagent

import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.Callable
import reagent.tester.testMany

class ManyKtTest {
  @Test fun fromCallable() {
    val called = AtomicBoolean()
    Callable { called.getAndSet(true) }
        .asMany()
        .testMany {
          item(false)
          complete()
        }
    assertTrue(called.get())
  }

  @Test fun fromCallableThrowing() {
    val exception = RuntimeException("Oops!")
    Callable { throw exception }
        .asMany()
        .testMany {
          error(exception)
        }
  }

  @Test
  fun fromRunnable() {
    val called = AtomicBoolean()
    Runnable { called.set(true) }
        .asMany<Any>()
        .testMany {
          complete()
        }
    assertTrue(called.get())
  }

  @Test
  fun fromRunnableThrowing() {
    val exception = RuntimeException("Oops!")
    Runnable { throw exception }
        .asMany<Any>()
        .testMany {
          error(exception)
        }
  }
}
