package reagent.operator

import reagent.runTest
import reagent.source.observable
import reagent.source.observableOf
import reagent.source.test.emptyActualObservable
import reagent.tester.testObservable
import reagent.tester.testOne
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class ObservableNoneTest {
  @Test fun empty() = runTest {
    emptyActualObservable<Any>()
        .none { fail() }
        .testOne {
          item(true)
        }
  }

  @Test fun success() = runTest {
    var called = 0
    observableOf(1, 2, 3)
        .none { called++; false }
        .testOne {
          item(true)
        }
    assertEquals(3, called)
  }

  @Test fun failure() = runTest {
    var called = 0
    observableOf(1, 2, 3)
        .none { called++; it % 2 == 0 }
        .testOne {
          item(false)
        }
    assertEquals(2, called)
  }

  @Test fun emitterReturnValue() = runTest {
    var result: Boolean? = null
    observable<Int> {
      result = it(1)
    }.none {
      true
    }.testObservable {
      item(false)
      complete()
    }
    assertEquals(false, result)
  }
}
