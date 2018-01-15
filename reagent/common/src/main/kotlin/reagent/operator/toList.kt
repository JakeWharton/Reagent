package reagent.operator

import reagent.Observable

suspend fun <I> Observable<I>.toList(): List<I> {
  val items = mutableListOf<I>()
  subscribe { items.add(it) }
  return items
}
