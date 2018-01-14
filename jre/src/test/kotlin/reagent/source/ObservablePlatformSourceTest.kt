package reagent.source

import kotlinx.coroutines.experimental.channels.Channel
import org.junit.Assert.assertTrue
import org.junit.Test
import reagent.runTest
import reagent.tester.testObservable
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicBoolean

class ObservablePlatformSourceTest {
  @Test fun fromCallable() = runTest {
    val called = AtomicBoolean()
    Callable { called.getAndSet(true) }
        .asMany()
        .testObservable {
          item(false)
          complete()
        }
    assertTrue(called.get())
  }

  @Test fun fromCallableThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    Callable { throw exception }
        .asMany()
        .testObservable {
          error(exception)
        }
  }

  @Test
  fun fromRunnable() = runTest {
    val called = AtomicBoolean()
    Runnable { called.set(true) }
        .asMany<Any>()
        .testObservable {
          complete()
        }
    assertTrue(called.get())
  }

  @Test
  fun fromRunnableThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    Runnable { throw exception }
        .asMany<Any>()
        .testObservable {
          error(exception)
        }
  }

  @Test fun channel() = runTest {
    val channel = Channel<String>(2)
    channel.send("Hello")
    channel.send("World")
    channel.close()

    channel.toMany()
        .testObservable {
          item("Hello")
          item("World")
          complete()
        }
  }
}
