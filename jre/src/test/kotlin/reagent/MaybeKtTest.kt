package reagent

import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicBoolean
import reagent.tester.testMaybe

class MaybeKtTest {
  @Test
  fun fromCallable() {
    val called = AtomicBoolean()
    Callable { called.getAndSet(true) }
        .asMaybe()
        .testMaybe {
          item(false)
        }
    assertTrue(called.get())
  }

  @Test
  fun fromCallableThrowing() {
    val exception = RuntimeException("Oops!")
    Callable { throw exception }
        .asMaybe()
        .testMaybe {
          error(exception)
        }
  }

  @Test fun fromRunnable() {
    val called = AtomicBoolean()
    Runnable { called.set(true) }
        .asMaybe<Any>()
        .testMaybe {
          nothing()
        }
    assertTrue(called.get())
  }

  @Test fun fromRunnableThrowing() {
    val exception = RuntimeException("Oops!")
    Runnable { throw exception }
        .asMaybe<Any>()
        .testMaybe {
          error(exception)
        }
  }
}
