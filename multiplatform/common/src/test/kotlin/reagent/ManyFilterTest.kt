package reagent

import reagent.operator.filter
import reagent.tester.testMany
import kotlin.test.Ignore
import kotlin.test.Test

class ManyFilterTest {
  @Test fun filter() {
    Many.fromArray("Hello", "World")
        .filter { it == "Hello" }
        .testMany {
          item("Hello")
          complete()
        }
  }

  @Test fun filterEmpty() {
    Many.empty<Nothing>()
        .filter { throw AssertionError() }
        .testMany {
          complete()
        }
  }

  @Test fun filterError() {
    val exception = RuntimeException("Oops!")
    Many.error<Nothing>(exception)
        .filter { throw AssertionError() }
        .testMany {
          error(exception)
        }
  }

  @Ignore // Error handling not implemented yet
  @Test fun filterThrowing() {
    val exception = RuntimeException("Oops!")
    Many.fromArray("Hello", "World")
        .filter { throw exception }
        .testMany {
          error(exception)
        }
  }
}
