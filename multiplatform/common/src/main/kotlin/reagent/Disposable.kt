package reagent

import reagent.internal.AtomicRef

interface Disposable {
  fun dispose()
  val isDisposed: Boolean

  companion object {
    //@JvmStatic
    fun disposed(): Disposable = Disposed
    //@JvmStatic
    fun empty(): Disposable = Empty()
  }

  private object Disposed : Disposable {
    override fun dispose() = Unit
    override val isDisposed get() = true
  }

  private class Empty : Disposable {
    @Volatile private var disposed = false

    override fun dispose() {
      disposed = true
    }

    override val isDisposed get() = disposed
  }
}

internal inline val AtomicRef<Disposable?>.isDisposed get() = get() == Marker

internal fun AtomicRef<Disposable?>.dispose(): Boolean {
  val current = get()
  val marker = Marker
  if (current != marker) {
    val replaced = getAndSet(marker)
    if (replaced != marker) {
      replaced?.dispose()
      return true
    }
  }
  return false
}

internal inline fun AtomicRef<Disposable?>.setOnceThen(value: Disposable, success: () -> Unit) {
  if (compareAndSet(null, value)) {
    success()
  } else {
    setOnceFailed(value)
  }
}

internal fun AtomicRef<Disposable?>.setOnceFailed(value: Disposable) {
  value.dispose()
  if (get() != Marker) {
    // TODO report error to global error handler?
  }
}

/** An always-disposed marker instance for internal use. DO NOT LEAK into public API or code. */
private object Marker : Disposable {
  override fun dispose() = Unit
  override val isDisposed get() = true
}
