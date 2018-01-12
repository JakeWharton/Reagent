package reagent

interface SuspendableIterator<out I> {
  suspend operator fun hasNext(): Boolean
  suspend operator fun next(): I
}
