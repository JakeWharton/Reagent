package reagent.rxjava2

import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.fail
import org.junit.Test
import kotlin.test.assertSame
import io.reactivex.Completable as RxCompletable

class TaskRxToReagentTest {
  @Test fun complete() = runBlocking {
    RxCompletable.complete()
        .toReagent()
        .run()
  }

  @Test fun error() = runBlocking {
    val exception = RuntimeException("Oops!")
    try {
      RxCompletable.error(exception)
          .toReagent()
          .run()
      fail()
    } catch (t: Throwable) {
      assertSame(exception, t)
    }
  }
}
