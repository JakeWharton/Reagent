package reagent.rxjava2

import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.fail
import org.junit.Test
import reagent.operator.toList
import kotlin.test.assertEquals
import kotlin.test.assertSame
import io.reactivex.Observable as RxObservable

class ObservableRxToReagentTest {
  @Test fun empty() = runBlocking {
    val value = RxObservable.empty<Any>()
        .toReagent()
        .toList()
    assertEquals(emptyList(), value)
  }

  @Test fun item() = runBlocking {
    val value = RxObservable.just("Hello")
        .toReagent()
        .toList()
    assertEquals(listOf("Hello"), value)
  }

  @Test fun items() = runBlocking {
    val value = RxObservable.just("Hello", "World")
        .toReagent()
        .toList()
    assertEquals(listOf("Hello", "World"), value)
  }

  @Test fun error() = runBlocking {
    val exception = RuntimeException("Oops!")
    try {
      RxObservable.error<Any>(exception)
          .toReagent()
          .toList()
      fail()
    } catch (t: Throwable) {
      assertSame(exception, t)
    }
  }
}
