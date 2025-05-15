package com.shashrwat.vault.test.core.base

import com.shashrwat.vault.test.core.interceptors.OpenMenuIfClosedInterceptor
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.params.FlakySafetyParams
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase

abstract class VaultTestCase : TestCase(Kaspresso.Builder.simple().apply {
  viewBehaviorInterceptors.add(
    OpenMenuIfClosedInterceptor(FlakySafetyParams.defaultAllowedExceptions)
  )
})
