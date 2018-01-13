package reagent

import kotlinx.coroutines.experimental.Job

interface Disposable {
  val isDisposed: Boolean
  fun dispose()
}

internal class JobDisposable(private val job: Job): Disposable {
  override val isDisposed get() = job.isCancelled
  override fun dispose() {
    job.cancel()
  }
}
