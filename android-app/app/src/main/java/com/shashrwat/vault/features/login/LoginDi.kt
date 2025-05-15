package com.shashrwat.vault.features.login

import com.shashrwat.vault.core.mvi.tea.TeaStore
import com.shashrwat.vault.core.mvi.tea.TeaStoreImpl
import com.shashrwat.vault.features.common.di.CoreComponent
import com.shashrwat.vault.features.login.actors.GetBiometricsEnterPossibleActor
import com.shashrwat.vault.features.login.actors.LoginRouterActor
import com.shashrwat.vault.features.login.actors.LoginWithBiometricsActor
import com.shashrwat.vault.features.login.actors.LoginWithPasswordActor

fun LoginStore(
  coreComponent: CoreComponent
): TeaStore<LoginState, LoginUiEvent, LoginNews> {
  return TeaStoreImpl(
    actors = listOf(
      GetBiometricsEnterPossibleActor(
        coreComponent.biometricsEnabledProvider,
        coreComponent.biometricsAllowedManager,
        coreComponent.biometricsStorage,
      ),
      LoginWithBiometricsActor(
        coreComponent.biometricsStorage,
        coreComponent.biometricsAllowedManager,
        coreComponent.masterPasswordChecker
      ),
      LoginWithPasswordActor(
        coreComponent.masterPasswordChecker,
        coreComponent.biometricsAllowedManager
      ),
      LoginRouterActor(coreComponent.router)
    ),
    reducer = LoginReducer(),
    initialState = LoginState()
  )
}
