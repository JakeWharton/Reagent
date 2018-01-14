package reagent.operator

import reagent.runTest
import reagent.source.emptyObservable
import reagent.source.observableOf
import reagent.tester.testObservable
import kotlin.test.Test
import kotlin.test.fail

class ObservableDistinctUntilChangedTest {
  @Test fun empty() = runTest {
    emptyObservable()
        .distinctUntilChanged()
        .testObservable {
          complete()
        }
  }

  @Test fun emptyBy() = runTest {
    emptyObservable()
        .distinctUntilChangedBy { fail() }
        .testObservable {
          complete()
        }
  }

  @Test fun multiple() = runTest {
    observableOf("Hello", "World", "World", "Hello", "Hello", "Hello", "World")
        .distinctUntilChanged()
        .testObservable {
          item("Hello")
          item("World")
          item("Hello")
          item("World")
          complete()
        }
  }

  @Test fun multipleBy() = runTest {
    observableOf("He", "Has", "Gone", "Galloping", "To", "The", "Head")
        .distinctUntilChangedBy { it[0] }
        .testObservable {
          item("He")
          item("Gone")
          item("To")
          item("Head")
          complete()
        }
  }
}
