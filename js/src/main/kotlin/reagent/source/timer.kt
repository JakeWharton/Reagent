package reagent.source

import reagent.One

actual fun timer(delayMillis: Int): One<Unit> = OneTimerInt(delayMillis)
