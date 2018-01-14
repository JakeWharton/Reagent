package reagent.operator

import reagent.One
import reagent.Task

private val unitSupplier = { Unit }

fun Task.thenReturnUnit(): One<Unit> = TaskThenReturn(this, unitSupplier)
fun <I> Task.thenReturn(item: I): One<I> = TaskThenReturn(this, { item })
fun <I> Task.thenReturn(supplier: () -> I): One<I> = TaskThenReturn(this, supplier)

internal class TaskThenReturn<out I>(
  private val upstream: Task,
  private val supplier: () -> I
): One<I>() {
  override suspend fun produce(): I {
    upstream.run()
    return supplier()
  }
}
