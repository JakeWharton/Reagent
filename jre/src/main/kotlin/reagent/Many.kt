package reagent

/** Emits 0 to infinite items and then signals complete or error. */
actual abstract class Many<out I> {
  actual abstract suspend fun subscribe(emitter: Emitter<I>)
}
