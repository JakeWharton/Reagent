package reagent.rxjava2

import io.reactivex.disposables.Disposable
import kotlinx.coroutines.experimental.Job

internal class JobDisposable(private val job: Job) : Disposable {
  override fun isDisposed() = job.isCancelled

  override fun dispose() {
    println("DISPOSED")
    job.cancel()
  }
}
