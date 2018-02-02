package reagent

/** Emits a single item or errors. */
actual abstract class One<out I> : Observable<I>() {
  actual abstract suspend fun produce(): I

  actual override suspend fun subscribe(emit: Emitter<I>) {
    emit(produce())
  }
}
