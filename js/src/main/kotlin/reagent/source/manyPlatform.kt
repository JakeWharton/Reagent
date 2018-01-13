package reagent.source

import reagent.Many

actual fun interval(periodMillis: Int): Many<Int> = ManyIntervalInt(periodMillis)
