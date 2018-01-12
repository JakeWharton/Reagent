package reagent

import reagent.operator.filter
import reagent.tester.testMaybe
import kotlin.test.Ignore
import kotlin.test.Test

class MaybeFilterTest {
  @Test fun filter() = runTest {
    Maybe.just("Hello")
        .filter { it == "Hello" }
        .testMaybe {
          item("Hello")
        }
  }

  @Test fun filterOut() = runTest {
    Maybe.just("Hello")
        .filter { it != "Hello" }
        .testMaybe {
          nothing()
        }
  }

  @Test
  fun filterEmpty() = runTest {
    Maybe.empty<Nothing>()
        .filter { throw AssertionError() }
        .testMaybe {
          nothing()
        }
  }

  @Test
  fun filterError() = runTest {
    val exception = RuntimeException("Oops!")
    Maybe.error<Nothing>(exception)
        .filter { throw AssertionError() }
        .testMaybe {
          error(exception)
        }
  }

  @Ignore // Error handling not implemented yet
  @Test fun filterThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    Maybe.just("Hello")
        .filter { throw exception }
        .testMaybe {
          error(exception)
        }
  }
}
