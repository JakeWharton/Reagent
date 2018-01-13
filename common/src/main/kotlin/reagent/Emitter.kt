package reagent

interface Emitter<in I> {
  suspend fun send(item: I)
}
