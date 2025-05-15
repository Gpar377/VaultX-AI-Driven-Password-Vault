package com.shashrwat.vault.features.master_password

import com.shashrwat.vault.core.mvi.tea.TeaStore
import com.shashrwat.vault.core.mvi.tea.TeaStoreImpl
import com.shashrwat.vault.features.common.di.CoreComponent
import com.shashrwat.vault.features.master_password.actors.ChangeExistingMasterPasswordActor
import com.shashrwat.vault.features.master_password.actors.CheckPasswordStatusActor
import com.shashrwat.vault.features.master_password.actors.CheckPasswordStrengthActor
import com.shashrwat.vault.features.master_password.actors.CreateNewMasterPasswordActor
import com.shashrwat.vault.features.master_password.actors.CreatingMasterPasswordRouterActor

fun MasterPasswordStore(
  coreComponent: CoreComponent,
  mode: MasterPasswordScreenMode
): TeaStore<MasterPasswordState, MasterPasswordUiEvent, MasterPasswordNews> {
  return TeaStoreImpl(
    actors = listOf(
      CheckPasswordStatusActor(
        coreComponent.masterPasswordProvider,
        coreComponent.passwordChecker
      ),
      CheckPasswordStrengthActor(coreComponent.passwordChecker),
      ChangeExistingMasterPasswordActor(
        coreComponent.masterPasswordProvider,
        coreComponent.observableCachedDatabaseStorage,
        coreComponent.storageBackupInteractor,
        coreComponent.changeMasterPasswordObserver
      ),
      CreateNewMasterPasswordActor(coreComponent.databaseInitializer),
      CreatingMasterPasswordRouterActor(coreComponent.router),
    ),
    reducer = MasterPasswordReducer(),
    initialState = MasterPasswordState(mode)
  )
}
