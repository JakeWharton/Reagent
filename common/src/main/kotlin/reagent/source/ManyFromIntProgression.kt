package reagent.source

import reagent.Emitter
import reagent.Many

internal class ManyFromIntProgression(private val progression: IntProgression): Many<Int>() {
  override suspend fun subscribe(emit: Emitter<Int>) {
    for (value in progression) {
      emit(value)
    }
  }
}
