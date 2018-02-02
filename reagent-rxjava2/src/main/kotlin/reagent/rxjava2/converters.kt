@file:JvmName("RxJava2Converters")

package reagent.rxjava2

import reagent.Observable
import reagent.One
import io.reactivex.Completable as RxCompletable
import io.reactivex.Maybe as RxMaybe
import io.reactivex.Observable as RxObservable
import io.reactivex.Single as RxSingle

fun <I : Any> Observable<I>.toRx(): RxObservable<I> = ObservableReagentToRx(this)
fun <I : Any> One<I>.toRx(): RxSingle<I> = OneReagentToRx(this)

fun <I : Any> RxObservable<I>.toReagent(): Observable<I> = ObservableRxToReagent(this)
fun <I : Any> RxMaybe<I>.toReagent(): One<I?> = MaybeRxToReagent(this)
fun <I : Any> RxSingle<I>.toReagent(): One<I> = SingleRxToReagent(this)
fun RxCompletable.toReagent(): One<Unit> = CompletableRxToReagent(this)
