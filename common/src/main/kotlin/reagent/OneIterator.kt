package reagent

operator fun <I> One<I>.iterator(): SuspendableIterator<I> = OneIterator(this)

internal class OneIterator<out I>(private val one: One<I>) : SuspendableIterator<I> {
  private var done = false

  override suspend fun hasNext() = !done

  override suspend fun next(): I {
    if (done) throw IllegalStateException("Must call hasNext() before next()")
    done = true
    return one.produce()
  }
}
