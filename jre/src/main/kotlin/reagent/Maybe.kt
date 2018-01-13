package reagent

/** Emits an item, signals nothing (no item), or signals error. */
actual abstract class Maybe<out I> : Many<I>() {
  actual abstract suspend fun produce(): I?

  actual override suspend fun subscribe(emitter: Emitter<I>) {
    produce()?.let { emitter.send(it) }
  }
}
