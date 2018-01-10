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
  interface Observer {
    suspend fun onComplete()
    suspend fun onError(t: Throwable)
  }

  abstract suspend fun subscribe(observer: Observer)
  override suspend fun subscribe(observer: Maybe.Observer<Nothing>) = subscribe(ObserverFromMaybe(observer))
  override suspend fun subscribe(observer: Many.Observer<Nothing>) = subscribe(ObserverFromMany(observer))

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
    override suspend fun subscribe(observer: Observer) = observer.onComplete()
  }

  internal class Error(private val t: Throwable) : Task() {
    override suspend fun subscribe(observer: Observer) = observer.onError(t)
  }

  internal class FromLambda(private val func: () -> Unit) : Task() {
    override suspend fun subscribe(observer: Observer) {
      try {
        func.invoke()
      } catch (t: Throwable) {
        observer.onError(t)
        return
      }
      observer.onComplete()
    }
  }

  internal class Deferred(private val func: () -> Task): Task() {
    override suspend fun subscribe(observer: Observer) = func().subscribe(observer)
  }

  internal class ObserverFromMaybe(private val delegate: Maybe.Observer<Nothing>) : Observer {
    override suspend fun onComplete() = delegate.onNothing()
    override suspend fun onError(t: Throwable) = delegate.onError(t)
  }

  internal class ObserverFromMany(private val delegate: Many.Observer<Nothing>): Observer {
    override suspend fun onComplete() = delegate.onComplete()
    override suspend fun onError(t: Throwable) = delegate.onError(t)
  }
}
