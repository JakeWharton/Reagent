package reagent.source

import kotlinx.coroutines.experimental.channels.Channel
import org.junit.Assert.assertTrue
import org.junit.Test
import reagent.runTest
import reagent.tester.testObservable
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicBoolean

class ObservablePlatformSourceTest {
  @Test fun channel() = runTest {
    val channel = Channel<String>(2)
    channel.send("Hello")
    channel.send("World")
    channel.close()

    channel.toObservable()
        .testObservable {
          item("Hello")
          item("World")
          complete()
        }
  }
}
