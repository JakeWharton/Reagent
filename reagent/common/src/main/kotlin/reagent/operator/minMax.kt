package reagent.operator

import reagent.Observable
import reagent.One
import kotlin.math.sign

fun <I : Comparable<I>> Observable<I>.max(): One<I> = ObservableComparing(this, 1, { it })
fun <I : Comparable<I>> Observable<I>.min(): One<I> = ObservableComparing(this, -1, { it })
fun <I, S : Comparable<S>> Observable<I>.maxBy(selector: (I) -> S): One<I> = ObservableComparing(this, 1, selector)
fun <I, S : Comparable<S>> Observable<I>.minBy(selector: (I) -> S): One<I> = ObservableComparing(this, -1, selector)

internal class ObservableComparing<out I, in S : Comparable<S>>(
  private val upstream: Observable<I>,
  private val order: Int,
  private val selector: (I) -> S
): One<I>() {
  @Suppress("UNCHECKED_CAST") // 'max' is set to an R instance before cast occurs.
  override suspend fun produce(): I {
    var first = true
    var max: Any? = null
    upstream.subscribe {
      if (first) {
        max = it
        first = false
      } else if (selector(it).compareTo(selector(max as I)).sign == order) {
        max = it
      }
    }
    if (first) {
      throw NoSuchElementException("No elements to compare")
    }
    return max as I
  }
}
