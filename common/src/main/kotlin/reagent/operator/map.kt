/*
 * Copyright 2017 Jake Wharton
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
package reagent.operator

import reagent.Many
import reagent.Maybe
import reagent.One
import reagent.Task
import reagent.internal.many.ManyMap
import reagent.internal.maybe.MaybeMap
import reagent.internal.one.OneMap
import kotlin.DeprecationLevel.ERROR

fun <I, O> Many<I>.map(func: (I) -> O): Many<O> = ManyMap(this, func)

fun <I, O> Maybe<I>.map(func: (I) -> O): Maybe<O> = MaybeMap(this, func)

fun <I, O> One<I>.map(func: (I) -> O): One<O> = OneMap(this, func)

@Deprecated("Task has no items so mapping does not make sense.", level = ERROR)
fun <O> Task.map(func: (Nothing) -> O): Task = this
