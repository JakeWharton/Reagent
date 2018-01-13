package reagent.operator

import reagent.Many

suspend fun <I> Many<I>.toList(): List<I> {
  val items = mutableListOf<I>()
  subscribe { items.add(it) }
  return items
}
