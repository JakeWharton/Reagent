package reagent.source

import org.junit.Assert.assertTrue
import org.junit.Test
import reagent.runTest
import reagent.tester.testOne
import java.time.Duration
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.system.measureTimeMillis

class OnePlatformSourceTest {
  @Test
  fun fromCallable() = runTest {
    val called = AtomicBoolean()
    Callable { called.getAndSet(true) }
        .asOne()
        .testOne {
          item(false)
        }
    assertTrue(called.get())
  }

  @Test
  fun fromCallableThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    Callable { throw exception }
        .asOne()
        .testOne {
          error(exception)
        }
  }

  @Test fun timerWithUnit() = runTest {
    // TODO need virtual time context to validate this works!
    val timer = timer(100, MILLISECONDS)
    val took = measureTimeMillis {
      timer.testOne { item(Unit) }
    }
    assertTrue(took >= 100)
  }

  @Test fun durationTimer() = runTest {
    // TODO need virtual time context to validate this works!
    val timer = Duration.ofMillis(100).asTimer()
    val took = measureTimeMillis {
      timer.testOne { item(Unit) }
    }
    assertTrue(took >= 100)
  }
}
