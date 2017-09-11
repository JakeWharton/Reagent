package reagent.internal

// TODO make 'internal' https://youtrack.jetbrains.com/issue/KT-19664
header class AtomicRef<V>(value: V) {
  fun get(): V
  fun set(value: V)
  fun getAndSet(value: V): V
  fun compareAndSet(expect: V, update: V): Boolean
}
