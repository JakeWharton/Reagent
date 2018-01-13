package reagent.operator

import reagent.Many

operator fun <I> Many<I>.iterator(): SuspendableIterator<I> = ManyIterator(this)

internal class ManyIterator<out I>(private val many: Many<I>) : SuspendableIterator<I> {
  // TODO i_have_no_idea_what_im_doing.gif

  override suspend fun hasNext() = TODO()
  override suspend fun next() = TODO()
}
