/*
 * Copyright 2016 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package reagent

import reagent.internal.many.ManyFromTask
import reagent.internal.maybe.MaybeFromTask
import reagent.internal.task.TaskComplete
import reagent.internal.task.TaskError
import reagent.internal.task.TaskFromLambda
import kotlin.DeprecationLevel.HIDDEN

/* Signals complete or error. Has no items. */
abstract class Task : Maybe<Nothing>() {
  interface Listener {
    fun onComplete()
    fun onError(t: Throwable)
  }

  abstract fun subscribe(listener: Listener)
  override fun subscribe(listener: Maybe.Listener<Nothing>) = toMaybe().subscribe(listener)

  /** Hide this `Task` instance as a `Many`. */
  override fun toMany(): Many<Nothing> = ManyFromTask(this)
  /** Hide this `Task` instance as a `Maybe`. */
  open fun toMaybe(): Maybe<Nothing> = MaybeFromTask(this)

  @Deprecated("Task has no items so mapping does not make sense.", level = HIDDEN)
  override fun <O> map(func: (Nothing) -> O): Maybe<O> = this

  @Deprecated("Task has no items so mapping does not make sense.", level = HIDDEN)
  override fun <O> flatMapMany(func: (Nothing) -> Many<O>): Many<O> = this
  @Deprecated("Task has no items so mapping does not make sense.", level = HIDDEN)
  override fun <O> flatMapMaybe(func: (Nothing) -> Maybe<O>): Maybe<O> = this
  @Deprecated("Task has no items so mapping does not make sense.", level = HIDDEN)
  override fun <O> flatMapOne(func: (Nothing) -> One<O>): Maybe<O> = this
  @Deprecated("Task has no items so mapping does not make sense.", level = HIDDEN)
  override fun flatMapTask(func: (Nothing) -> Task): Task = this

  companion object Factory {
    //@JvmStatic
    fun empty(): Task = TaskComplete
    //@JvmStatic
    fun error(t: Throwable): Task = TaskError(t)
    //@JvmStatic
    fun running(func: () -> Unit): Task = TaskFromLambda(func)
  }
}
