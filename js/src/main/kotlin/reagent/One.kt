package reagent

/** Emits a single item or errors. */
actual abstract class One<out I> : Maybe<I>() {
  actual abstract override suspend fun produce(): I

  actual override suspend fun subscribe(emitter: Emitter<I>) {
    emitter.send(produce())
  }
}
