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

/** Signals complete or error. Has no items. */
abstract class Task : Maybe<Nothing>() {
  abstract suspend fun run()

  override suspend fun produce(): Nothing? {
    run()
    return null
  }

  override suspend fun subscribe(emitter: Emitter<Nothing>) = run()

  companion object Factory {
    //@JvmStatic
    fun empty(): Task = Complete
    //@JvmStatic
    fun error(t: Throwable): Task = Error(t)
    //@JvmStatic
    fun running(func: () -> Unit): Task = FromLambda(func)
    //@JvmStatic
    fun defer(func: () -> Task): Task = Deferred(func)
  }

  internal object Complete : Task() {
    override suspend fun run() = Unit
  }

  internal class Error(private val t: Throwable) : Task() {
    override suspend fun run() = throw t
  }

  internal class FromLambda(private val func: () -> Unit) : Task() {
    override suspend fun run() = func()
  }

  internal class Deferred(private val func: () -> Task): Task() {
    override suspend fun run() = func().run()
  }
}
