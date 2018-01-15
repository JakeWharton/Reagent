package reagent.operator

import reagent.runTest
import reagent.source.observableOf
import kotlin.test.Test
import kotlin.test.assertEquals

class ObservableToListTest {
  @Test fun toList() = runTest {
    val list = observableOf(1, 2, 3).toList()
    assertEquals(listOf(1, 2, 3), list)
  }
}
