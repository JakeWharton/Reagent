package reagent

import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicBoolean
import reagent.tester.testOne

class OneKtTest {
  @Test
  fun fromCallable() {
    val called = AtomicBoolean()
    Callable { called.getAndSet(true) }
        .asOne()
        .testOne {
          item(false)
        }
    assertTrue(called.get())
  }

  @Test
  fun fromCallableThrowing() {
    val exception = RuntimeException("Oops!")
    Callable { throw exception }
        .asOne()
        .testOne {
          error(exception)
        }
  }
}
