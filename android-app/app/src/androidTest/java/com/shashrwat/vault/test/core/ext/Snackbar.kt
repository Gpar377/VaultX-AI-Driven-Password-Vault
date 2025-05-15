package com.shashrwat.vault.test.core.ext

import android.os.SystemClock
import com.shashrwat.vault.features.common.Durations


fun waitForSnackbarToHide() {
  SystemClock.sleep(Durations.Snackbar * 3)
}