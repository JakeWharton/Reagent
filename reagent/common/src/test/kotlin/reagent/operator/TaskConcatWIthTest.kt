package reagent.operator

import reagent.runTest
import reagent.source.emptyObservable
import reagent.source.observableRunning
import reagent.tester.testTask
import kotlin.test.Test
import kotlin.test.assertTrue

class TaskConcatWIthTest {
  @Test fun concatWith() = runTest {
    var called = false
    emptyObservable()
        .concatWith(observableRunning { called = true })
        .testTask {
          complete()
        }
    assertTrue(called)
  }
}
