package reagent.rxjava2

import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.fail
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import io.reactivex.Single as RxSingle

class SingleRxToReagentTest {
  @Test fun item() = runBlocking {
    val value = RxSingle.just("Hello")
        .toReagent()
        .produce()
    assertEquals("Hello", value)
  }

  @Test fun error() = runBlocking {
    val exception = RuntimeException("Oops!")
    try {
      RxSingle.error<Any>(exception)
          .toReagent()
          .produce()
      fail()
    } catch (t: Throwable) {
      assertSame(exception, t)
    }
  }
}
