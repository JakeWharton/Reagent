package reagent.rxjs6

import kotlinx.coroutines.experimental.CoroutineStart.UNDISPATCHED
import kotlinx.coroutines.experimental.JobCancellationException
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.launch
import reagent.Observable

fun <I> Observable<I>.toRx(): dynamic {
  return Rx.Observable.create<I> { observer ->
    val job = launch(Unconfined, UNDISPATCHED) {
      try {
        this@toRx.subscribe {
          observer.onNext(it)
          true // TODO
        }
      } catch (ignored: JobCancellationException) {
        return@launch
      } catch (t: Throwable) {
        observer.onError(t)
        return@launch
      }
      observer.onComplete()
    }
    return@create { job.cancel() }
  }
}

@JsModule("Rx")
private external object Rx {
  object Observable {
    fun <I> create(body: (Observer<I>) -> TeardownLogic)
  }

  interface Observer<in I> {
    fun onNext(value: I)
    fun onComplete()
    fun onError(t: Throwable)
  }
}

private typealias TeardownLogic = () -> Unit
