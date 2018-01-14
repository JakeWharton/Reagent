package reagent.source

import reagent.Emitter
import reagent.Many

internal class ManyFromLongProgression(private val progression: LongProgression): Many<Long>() {
  override suspend fun subscribe(emit: Emitter<Long>) {
    for (value in progression) {
      emit(value)
    }
  }
}
