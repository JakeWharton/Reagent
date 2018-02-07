package reagent.source

import reagent.Task

internal class TaskJust<out I>(private val item: I) : Task<I>() {
  override suspend fun produce() = item
}
