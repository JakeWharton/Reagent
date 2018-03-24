package reagent

/** Emits 0 to infinite items and then signals complete or error. */
actual abstract class Observable<out I> {
  actual abstract suspend fun subscribe(emit: Emitter<I>)
}
