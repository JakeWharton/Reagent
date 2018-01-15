package reagent.source

import reagent.Task

internal object TaskComplete : Task() {
  override suspend fun run() = Unit
}
