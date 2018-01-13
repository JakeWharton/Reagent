package reagent.operator

import reagent.runTest
import reagent.source.manyOf
import kotlin.test.Test
import kotlin.test.assertEquals

class ManyToListTest {
  @Test fun toList() = runTest {
    val list = manyOf(1, 2, 3).toList()
    assertEquals(listOf(1, 2, 3), list)
  }
}
