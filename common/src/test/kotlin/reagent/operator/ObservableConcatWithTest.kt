package reagent.operator

import reagent.runTest
import reagent.source.observableOf
import reagent.tester.testObservable
import kotlin.test.Test

class ObservableConcatWithTest {
  @Test fun concatWith() = runTest {
    observableOf(1, 2)
        .concatWith(observableOf(3, 4))
        .testObservable {
          item(1)
          item(2)
          item(3)
          item(4)
          complete()
        }
  }
}
