package reagent.source

import reagent.Emitter
import reagent.Many

internal class ManyFromCharProgression(private val progression: CharProgression): Many<Char>() {
  override suspend fun subscribe(emit: Emitter<Char>) {
    for (value in progression) {
      emit(value)
    }
  }
}
