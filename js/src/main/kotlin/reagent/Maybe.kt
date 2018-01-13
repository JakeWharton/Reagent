package reagent

/** Emits an item, signals nothing (no item), or signals error. */
actual abstract class Maybe<out I> : Many<I>() {
  actual abstract suspend fun produce(): I?

  actual override suspend fun subscribe(emit: Emitter<I>) {
    produce()?.let { emit(it) }
  }
}
