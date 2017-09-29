package reagent.internal

actual class AtomicRef<V> actual constructor(private var value: V) {
  actual fun get(): V = this.value

  actual fun set(value: V) {
    this.value = value
  }

  actual fun getAndSet(value: V): V {
    val old = this.value
    this.value = value
    return old
  }

  actual fun compareAndSet(expect: V, update: V): Boolean {
    if (this.value == expect) {
      this.value = update
      return true
    }
    return false
  }
}
