package reagent

import org.junit.Test
import reagent.operator.filter
import reagent.tester.testMaybe

class OneFilterTest {
  @Test fun filter() {
    One.just("Hello")
        .filter { it == "Hello" }
        .testMaybe {
          item("Hello")
        }
  }
  @Test fun filterOut() {
    One.just("Hello")
        .filter { it != "Hello" }
        .testMaybe {
          nothing()
        }
  }

  @Test fun filterError() {
    val exception = RuntimeException("Oops!")
    One.error<Nothing>(exception)
        .filter { throw AssertionError() }
        .testMaybe {
          error(exception)
        }
  }

//  @Ignore("Error handling not implemented yet")
//  @Test fun mapThrowing() {
//    val exception = RuntimeException("Oops!")
//    PureOne.just("Hello")
//        .filter { throw exception }
//        .testMaybe {
//          error(exception)
//        }
//  }
}
