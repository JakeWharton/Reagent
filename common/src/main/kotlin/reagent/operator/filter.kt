package reagent.operator

import reagent.Many
import reagent.Maybe
import reagent.One
import reagent.Task
import kotlin.DeprecationLevel.ERROR

fun <I> Many<I>.filter(predicate: (I) -> Boolean): Many<I> = ManyFilter(this, predicate)

fun <I> Maybe<I>.filter(predicate: (I) -> Boolean): Maybe<I> = MaybeFilter(this, predicate)

fun <I> One<I>.filter(predicate: (I) -> Boolean): Maybe<I> = OneFilter(this, predicate)

@Suppress("DeprecatedCallableAddReplaceWith") // TODO https://youtrack.jetbrains.com/issue/KT-19512
@Deprecated("Task has no items so filtering does not make sense.", level = ERROR)
fun Task.filter(predicate: (Nothing) -> Boolean) = this

internal class ManyFilter<out I>(
    private val upstream: Many<I>,
    private val predicate: (I) -> Boolean
) : Many<I>() {
  override fun subscribe(listener: Listener<I>) {
    upstream.subscribe(Operator(listener, predicate))
  }

  class Operator<in I>(
      private val downstream: Listener<I>,
      private val predicate: (I) -> Boolean
  ) : Many.Listener<I> {
    override fun onNext(item: I) {
      if (predicate(item)) {
        downstream.onNext(item)
      }
    }

    override fun onComplete() = downstream.onComplete()
    override fun onError(t: Throwable) = downstream.onError(t)
  }
}

internal class MaybeFilter<out I>(
    private val upstream: Maybe<I>,
    private val predicate: (I) -> Boolean
) : Maybe<I>() {
  override fun subscribe(listener: Listener<I>) {
    upstream.subscribe(Operator(listener, predicate))
  }

  class Operator<in I>(
      private val downstream: Listener<I>,
      private val predicate: (I) -> Boolean
  ) : Maybe.Listener<I> {
    override fun onItem(item: I) {
      if (predicate(item)) {
        downstream.onItem(item)
      } else {
        downstream.onNothing()
      }
    }

    override fun onNothing() = downstream.onNothing()
    override fun onError(t: Throwable) = downstream.onError(t)
  }
}

internal class OneFilter<out I>(
    private val upstream: One<I>,
    private val predicate: (I) -> Boolean
) : Maybe<I>() {
  override fun subscribe(listener: Listener<I>) {
    upstream.subscribe(Operator(listener, predicate))
  }

  class Operator<in I>(
      private val downstream: Listener<I>,
      private val predicate: (I) -> Boolean
  ) : One.Listener<I> {
    override fun onItem(item: I) {
      if (predicate(item)) {
        downstream.onItem(item)
      } else {
        downstream.onNothing()
      }
    }

    override fun onError(t: Throwable) = downstream.onError(t)
  }
}
