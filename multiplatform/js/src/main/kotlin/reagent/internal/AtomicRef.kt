package reagent.internal

impl class AtomicRef<V>(private var value: V) {
  impl fun get(): V = this.value

  impl fun set(value: V) {
    this.value = value
  }

  impl fun getAndSet(value: V): V {
    val old = this.value
    this.value = value
    return old
  }

  impl fun compareAndSet(expect: V, update: V): Boolean {
    if (this.value == expect) {
      this.value = update
      return true
    }
    return false
  }
}
