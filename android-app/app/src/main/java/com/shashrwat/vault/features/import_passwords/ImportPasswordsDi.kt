package com.shashrwat.vault.features.import_passwords

import android.net.Uri
import com.shashrwat.vault.core.mvi.tea.TeaStore
import com.shashrwat.vault.core.mvi.tea.TeaStoreImpl
import com.shashrwat.vault.features.common.di.CoreComponent
import com.shashrwat.vault.features.import_passwords.actors.ImportPasswordsActor
import com.shashrwat.vault.features.import_passwords.actors.ImportPasswordsRouterActor

fun ImportPasswordsStore(
  coreComponent: CoreComponent,
  selectedFileUri: Uri,
  askForConfirmation: Boolean,
): TeaStore<ImportPasswordsState, ImportPasswordsUiEvent, Nothing> {
  return TeaStoreImpl(
    actors = listOf(
      ImportPasswordsActor(
        coreComponent.externalFileReader,
        coreComponent.keyFileSaver,
        coreComponent.observableCachedDatabaseStorage,
        coreComponent.storageBackupInteractor
      ),
      ImportPasswordsRouterActor(coreComponent.router),
    ),
    reducer = ImportPasswordsReducer(),
    initialState = ImportPasswordsState(selectedFileUri, askForConfirmation = askForConfirmation)
  )
}

