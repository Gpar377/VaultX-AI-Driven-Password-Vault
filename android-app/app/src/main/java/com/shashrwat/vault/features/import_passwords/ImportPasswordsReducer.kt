package com.shashrwat.vault.features.import_passwords

import com.shashrwat.vault.core.mvi.tea.DslReducer
import com.shashrwat.vault.features.import_passwords.ImportPasswordsCommand.RouterCommand.GoBack
import com.shashrwat.vault.features.import_passwords.ImportPasswordsCommand.RouterCommand.GoToMainListScreen
import com.shashrwat.vault.features.import_passwords.ImportPasswordsCommand.TryImportPasswords
import com.shashrwat.vault.features.import_passwords.ImportPasswordsEvent.PasswordsImportFailure
import com.shashrwat.vault.features.import_passwords.ImportPasswordsEvent.PasswordsImportSuccess
import com.shashrwat.vault.features.import_passwords.ImportPasswordsInfoDialog.CONFIRMATION
import com.shashrwat.vault.features.import_passwords.ImportPasswordsInfoDialog.FAILURE
import com.shashrwat.vault.features.import_passwords.ImportPasswordsUiEvent.OnBackPressed
import com.shashrwat.vault.features.import_passwords.ImportPasswordsUiEvent.OnClearKeyFileClicked
import com.shashrwat.vault.features.import_passwords.ImportPasswordsUiEvent.OnConfirmedImportClicked
import com.shashrwat.vault.features.import_passwords.ImportPasswordsUiEvent.OnHideErrorDialog
import com.shashrwat.vault.features.import_passwords.ImportPasswordsUiEvent.OnHideInfoDialog
import com.shashrwat.vault.features.import_passwords.ImportPasswordsUiEvent.OnHidePasswordEnteringDialog
import com.shashrwat.vault.features.import_passwords.ImportPasswordsUiEvent.OnImportPasswordsClicked
import com.shashrwat.vault.features.import_passwords.ImportPasswordsUiEvent.OnPasswordEntered
import com.shashrwat.vault.features.import_passwords.ImportPasswordsUiEvent.OnSelectedKeyFile
import com.shashrwat.vault.features.import_passwords.ImportPasswordsUiEvent.OnSelectedPasswordsFile

class ImportPasswordsReducer : DslReducer<ImportPasswordsState, ImportPasswordsEvent,
    ImportPasswordsCommand, Nothing>() {
  
  override fun dslReduce(event: ImportPasswordsEvent) {
    when (event) {
      is OnSelectedPasswordsFile -> {
        state { copy(passwordsFileUri = event.uri) }
      }
      is OnSelectedKeyFile -> {
        state { copy(keyFileUri = event.uri) }
      }
      OnImportPasswordsClicked -> {
        when {
          state.askForConfirmation -> state { copy(infoDialog = CONFIRMATION) }
          else -> state { copy(showEnterPasswordDialog = true) }
        }
      }
      OnClearKeyFileClicked -> {
        state { copy(keyFileUri = null) }
      }
      OnConfirmedImportClicked -> {
        state { copy(infoDialog = null, showEnterPasswordDialog = true) }
      }
      is OnPasswordEntered -> {
        state { copy(enteredPassword = event.password, showLoading = true) }
        commands(
          TryImportPasswords(
            checkNotNull(state.passwordsFileUri),
            state.keyFileUri,
            event.password
          )
        )
      }
      OnHideInfoDialog, OnHideErrorDialog -> {
        state { copy(infoDialog = null) }
      }
      OnHidePasswordEnteringDialog -> {
        state { copy(showEnterPasswordDialog = false) }
      }
      OnBackPressed -> {
        when {
          state.showLoading -> return
          state.infoDialog != null -> state { copy(infoDialog = null) }
          state.showEnterPasswordDialog -> state { copy(showEnterPasswordDialog = false) }
          else -> commands(GoBack)
        }
      }
      PasswordsImportFailure -> {
        state { copy(infoDialog = FAILURE, showLoading = false) }
      }
      PasswordsImportSuccess -> {
        commands(GoToMainListScreen)
      }
    }
  }
}
