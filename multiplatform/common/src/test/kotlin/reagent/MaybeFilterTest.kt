package reagent

import reagent.operator.filter
import reagent.tester.testMaybe
import kotlin.test.Ignore
import kotlin.test.Test

class MaybeFilterTest {
  @Test fun filter() {
    Maybe.just("Hello")
        .filter { it == "Hello" }
        .testMaybe {
          item("Hello")
        }
  }

  @Test fun filterOut() {
    Maybe.just("Hello")
        .filter { it != "Hello" }
        .testMaybe {
          nothing()
        }
  }

  @Test
  fun filterEmpty() {
    Maybe.empty<Nothing>()
        .filter { throw AssertionError() }
        .testMaybe {
          nothing()
        }
  }

  @Test
  fun filterError() {
    val exception = RuntimeException("Oops!")
    Maybe.error<Nothing>(exception)
        .filter { throw AssertionError() }
        .testMaybe {
          error(exception)
        }
  }

  @Ignore // Error handling not implemented yet
  @Test fun filterThrowing() {
    val exception = RuntimeException("Oops!")
    Maybe.just("Hello")
        .filter { throw exception }
        .testMaybe {
          error(exception)
        }
  }
}
