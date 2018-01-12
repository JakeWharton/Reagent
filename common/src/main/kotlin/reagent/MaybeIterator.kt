package reagent

operator fun <I> Maybe<I>.iterator(): SuspendableIterator<I> = MaybeIterator(this)

internal class MaybeIterator<out I>(private val maybe: Maybe<I>) : SuspendableIterator<I> {
  private var done = false
  private var value: I? = null

  override suspend fun hasNext(): Boolean {
    if (done) return false
    done = true
    value = maybe.produce()
    return value != null
  }

  override suspend fun next(): I {
    val value = if (done) {
      value
    } else {
      done = true
      maybe.produce()
    }
    value?.let {
      this.value = null
      return it
    }
    throw IllegalStateException("Must call hasNext() before next()")
  }
}
