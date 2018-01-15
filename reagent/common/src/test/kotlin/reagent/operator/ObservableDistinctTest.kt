package reagent.operator

import reagent.runTest
import reagent.source.emptyObservable
import reagent.source.observableOf
import reagent.tester.testObservable
import kotlin.test.Test
import kotlin.test.fail

class ObservableDistinctTest {
  @Test fun empty() = runTest {
    emptyObservable()
        .distinct()
        .testObservable {
          complete()
        }
  }

  @Test fun emptyBy() = runTest {
    emptyObservable()
        .distinctBy { fail() }
        .testObservable {
          complete()
        }
  }

  @Test fun multiple() = runTest {
    observableOf(1, 2, 1, 3, 2, 4, 3, 5, 4, 5)
        .distinct()
        .testObservable {
          item(1)
          item(2)
          item(3)
          item(4)
          item(5)
          complete()
        }
  }

  @Test fun multipleBy() = runTest {
    observableOf("Hey", "There", "Hello", "To", "You", "There's", "Hats")
        .distinctBy { it[0] }
        .testObservable {
          item("Hey")
          item("There")
          item("You")
          complete()
        }
  }
}
