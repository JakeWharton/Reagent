package reagent.operator

import reagent.runTest
import reagent.source.observableOf
import reagent.source.observable
import reagent.source.test.emptyActualObservable
import reagent.tester.testObservable
import reagent.tester.testOne
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class ObservableAnyTest {
  @Test fun empty() = runTest {
    emptyActualObservable<Any>()
        .any { fail() }
        .testOne {
          item(false)
        }
  }

  @Test fun success() = runTest {
    observableOf(1, 2, 3)
        .any { it == 2 }
        .testOne {
          item(true)
        }
  }

  @Test fun failure() = runTest {
    observableOf(1, 2, 3)
        .all { false }
        .testOne {
          item(false)
        }
  }

  @Test fun emitterReturnValue() = runTest {
    var result: Boolean? = null
    observable<Int> {
      result = it(1)
    }.any {
      true
    }.testObservable {
      item(true)
      complete()
    }
    assertEquals(false, result)
  }
}
