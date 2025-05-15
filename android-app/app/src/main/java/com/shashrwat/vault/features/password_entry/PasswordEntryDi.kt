package com.shashrwat.vault.features.password_entry

import com.shashrwat.vault.core.mvi.tea.TeaStore
import com.shashrwat.vault.core.mvi.tea.TeaStoreImpl
import com.shashrwat.vault.features.common.di.CoreComponent
import com.shashrwat.vault.features.password_entry.PasswordEntryState.ExistingEntry
import com.shashrwat.vault.features.password_entry.PasswordEntryState.NewEntry
import com.shashrwat.vault.features.password_entry.actors.CopyPasswordEntryActor
import com.shashrwat.vault.features.password_entry.actors.DeletePasswordEntryActor
import com.shashrwat.vault.features.password_entry.actors.FetchPasswordEntryActor
import com.shashrwat.vault.features.password_entry.actors.ListenNetworkAvailabilityActor
import com.shashrwat.vault.features.password_entry.actors.ListenPasswordChangesActor
import com.shashrwat.vault.features.password_entry.actors.PasswordEntryRouterActor
import com.shashrwat.vault.features.password_entry.actors.SavingPasswordEntryActor
import com.shashrwat.vault.features.password_entry.actors.SetupCreatingPasswordScreenActor
import com.shashrwat.vault.features.password_entry.actors.UpdatePasswordEntryActor

fun PasswordEntryStore(
  coreComponent: CoreComponent,
  passwordId: String?,
): TeaStore<PasswordEntryState, PasswordEntryUiEvent, PasswordEntryNews> {
  return TeaStoreImpl(
    actors = listOf(
      FetchPasswordEntryActor(
        coreComponent.masterPasswordProvider,
        coreComponent.observableCachedDatabaseStorage,
        coreComponent.keePassPasswordEntryInteractor,
      ),
      UpdatePasswordEntryActor(
        coreComponent.masterPasswordProvider,
        coreComponent.observableCachedDatabaseStorage,
        coreComponent.keePassPasswordEntryInteractor,
      ),
      DeletePasswordEntryActor(
        coreComponent.masterPasswordProvider,
        coreComponent.observableCachedDatabaseStorage,
      ),
      SavingPasswordEntryActor(
        coreComponent.masterPasswordProvider,
        coreComponent.observableCachedDatabaseStorage,
        coreComponent.keePassPasswordEntryInteractor
      ),
      SetupCreatingPasswordScreenActor(coreComponent.creatingPasswordSetupObserver),
      ListenPasswordChangesActor(coreComponent.passwordObserver),
      ListenNetworkAvailabilityActor(coreComponent.networkAvailabilityProvider),
      CopyPasswordEntryActor(coreComponent.clipboard),
      PasswordEntryRouterActor(coreComponent.router),
    ),
    reducer = PasswordEntryReducer(),
    initialState = if (passwordId != null) ExistingEntry(passwordId) else NewEntry()
  )
}
