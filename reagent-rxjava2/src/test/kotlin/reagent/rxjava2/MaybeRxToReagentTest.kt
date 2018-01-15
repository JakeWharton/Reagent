package reagent.rxjava2

import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.fail
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame
import io.reactivex.Maybe as RxMaybe

class MaybeRxToReagentTest {
  @Test fun complete() = runBlocking {
    val value = RxMaybe.empty<Any>()
        .toReagent()
        .produce()
    assertNull(value)
  }

  @Test fun item() = runBlocking {
    val value = RxMaybe.just("Hello")
        .toReagent()
        .produce()
    assertEquals("Hello", value)
  }

  @Test fun error() = runBlocking {
    val exception = RuntimeException("Oops!")
    try {
      RxMaybe.error<Any>(exception)
          .toReagent()
          .produce()
      fail()
    } catch (t: Throwable) {
      assertSame(exception, t)
    }
  }
}
