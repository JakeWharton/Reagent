package reagent

/** Signals complete or error. Has no items. */
actual abstract class Task : Maybe<Nothing>() {
  actual abstract suspend fun run()

  actual override suspend fun produce(): Nothing? {
    run()
    return null
  }

  actual override suspend fun subscribe(emitter: Emitter<Nothing>) = run()
}
