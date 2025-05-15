package com.shashrwat.vault.features.creating_password

import com.shashrwat.vault.core.mvi.tea.TeaStore
import com.shashrwat.vault.core.mvi.tea.TeaStoreImpl
import com.shashrwat.vault.features.common.di.CoreComponent
import com.shashrwat.vault.features.creating_password.actors.CheckPasswordStrengthActor
import com.shashrwat.vault.features.creating_password.actors.ComputePasswordCharacteristicsActor
import com.shashrwat.vault.features.creating_password.actors.CreatingPasswordRouterActor
import com.shashrwat.vault.features.creating_password.actors.GeneratePasswordActor
import com.shashrwat.vault.features.creating_password.actors.SendPasswordChangesActor

fun CreatingPasswordStore(
  coreComponent: CoreComponent
): TeaStore<CreatingPasswordState, CreatingPasswordUiEvent, CreatingPasswordNews> {
  return TeaStoreImpl(
    actors = listOf(
      GeneratePasswordActor(coreComponent.passwordGenerator),
      CheckPasswordStrengthActor(coreComponent.passwordChecker),
      ComputePasswordCharacteristicsActor(coreComponent.passwordCharacteristicsProvider),
      SendPasswordChangesActor(coreComponent.passwordObserver),
      CreatingPasswordRouterActor(coreComponent.router)
    ),
    reducer = CreatingPasswordReducer(),
    initialState = CreatingPasswordState()
  )
}
