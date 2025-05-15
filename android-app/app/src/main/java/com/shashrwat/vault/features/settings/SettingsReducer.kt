package com.shashrwat.vault.features.settings

import com.shashrwat.vault.core.mvi.tea.DslReducer
import com.shashrwat.vault.features.common.biometrics.BiometricsEvent
import com.shashrwat.vault.features.common.biometrics.BiometricsEvent.ErrorType
import com.shashrwat.vault.features.settings.EnterPasswordDialogState.HIDDEN
import com.shashrwat.vault.features.settings.EnterPasswordDialogState.HIDDEN_KEEPING_KEYBOARD
import com.shashrwat.vault.features.settings.EnterPasswordDialogState.SHOWN
import com.shashrwat.vault.features.settings.SettingsBiometricsError.LOCKOUT
import com.shashrwat.vault.features.settings.SettingsBiometricsError.LOCKOUT_PERMANENT
import com.shashrwat.vault.features.settings.SettingsBiometricsError.OTHER
import com.shashrwat.vault.features.settings.SettingsCommand.BackupCommand.DisableStorageBackup
import com.shashrwat.vault.features.settings.SettingsCommand.BackupCommand.EnableStorageBackup
import com.shashrwat.vault.features.settings.SettingsCommand.BackupCommand.PerformBackup
import com.shashrwat.vault.features.settings.SettingsCommand.ChangeImagesLoadingEnabled
import com.shashrwat.vault.features.settings.SettingsCommand.ChangeShowUsernames
import com.shashrwat.vault.features.settings.SettingsCommand.ClearImagesCache
import com.shashrwat.vault.features.settings.SettingsCommand.DisableBiometrics
import com.shashrwat.vault.features.settings.SettingsCommand.EnableBiometrics
import com.shashrwat.vault.features.settings.SettingsCommand.FetchData
import com.shashrwat.vault.features.settings.SettingsCommand.FetchStorageBackupInfo
import com.shashrwat.vault.features.settings.SettingsCommand.RouterCommand.GoBack
import com.shashrwat.vault.features.settings.SettingsCommand.RouterCommand.GoToMasterPasswordScreen
import com.shashrwat.vault.features.settings.SettingsEvent.BiometricsEnabled
import com.shashrwat.vault.features.settings.SettingsEvent.ImagesCacheCleared
import com.shashrwat.vault.features.settings.SettingsEvent.ImagesLoadingEnabledChanged
import com.shashrwat.vault.features.settings.SettingsEvent.PerformedBackup
import com.shashrwat.vault.features.settings.SettingsEvent.ReceivedBiometricsAvailable
import com.shashrwat.vault.features.settings.SettingsEvent.ReceivedBiometricsEnabled
import com.shashrwat.vault.features.settings.SettingsEvent.ReceivedImagesLoadingEnabled
import com.shashrwat.vault.features.settings.SettingsEvent.ReceivedShowUsernames
import com.shashrwat.vault.features.settings.SettingsEvent.ReceivedStorageBackupEnabled
import com.shashrwat.vault.features.settings.SettingsEvent.StorageBackupDisabled
import com.shashrwat.vault.features.settings.SettingsEvent.StorageBackupEnabled
import com.shashrwat.vault.features.settings.SettingsNews.LaunchFolderSelection
import com.shashrwat.vault.features.settings.SettingsNews.SetBiometricsEnabled
import com.shashrwat.vault.features.settings.SettingsNews.SetImagesLoadingEnabled
import com.shashrwat.vault.features.settings.SettingsNews.SetShowUsernames
import com.shashrwat.vault.features.settings.SettingsNews.SetStorageBackupEnabled
import com.shashrwat.vault.features.settings.SettingsNews.ShowBackupPerformed
import com.shashrwat.vault.features.settings.SettingsNews.ShowBiometricsEnabled
import com.shashrwat.vault.features.settings.SettingsNews.ShowBiometricsError
import com.shashrwat.vault.features.settings.SettingsNews.ShowBiometricsPrompt
import com.shashrwat.vault.features.settings.SettingsNews.ShowImagesCacheCleared
import com.shashrwat.vault.features.settings.SettingsNews.ShowMasterPasswordChanged
import com.shashrwat.vault.features.settings.SettingsNews.ShowStorageBackupEnabled
import com.shashrwat.vault.features.settings.SettingsUiEvent.OnAppearedOnScreen
import com.shashrwat.vault.features.settings.SettingsUiEvent.OnBackPressed
import com.shashrwat.vault.features.settings.SettingsUiEvent.OnBackupNowClicked
import com.shashrwat.vault.features.settings.SettingsUiEvent.OnBiometricsEvent
import com.shashrwat.vault.features.settings.SettingsUiEvent.OnChangeMasterPasswordClicked
import com.shashrwat.vault.features.settings.SettingsUiEvent.OnClearImagesCacheClicked
import com.shashrwat.vault.features.settings.SettingsUiEvent.OnEnableBiometricsChanged
import com.shashrwat.vault.features.settings.SettingsUiEvent.OnEnableImagesLoadingChanged
import com.shashrwat.vault.features.settings.SettingsUiEvent.OnEnableStorageBackupChanged
import com.shashrwat.vault.features.settings.SettingsUiEvent.OnEnteredPasswordToChangeMasterPassword
import com.shashrwat.vault.features.settings.SettingsUiEvent.OnHideEnableBiometricsDialog
import com.shashrwat.vault.features.settings.SettingsUiEvent.OnHideEnterPasswordDialog
import com.shashrwat.vault.features.settings.SettingsUiEvent.OnInit
import com.shashrwat.vault.features.settings.SettingsUiEvent.OnMasterPasswordChangedReceived
import com.shashrwat.vault.features.settings.SettingsUiEvent.OnProceedEnableBiometricsDialog
import com.shashrwat.vault.features.settings.SettingsUiEvent.OnSelectBackupFolderClicked
import com.shashrwat.vault.features.settings.SettingsUiEvent.OnSelectedBackupFolder
import com.shashrwat.vault.features.settings.SettingsUiEvent.OnShowUsernamesChanged

class SettingsReducer : DslReducer<SettingsState, SettingsEvent,
    SettingsCommand, SettingsNews>() {
  
  override fun dslReduce(event: SettingsEvent) {
    when (event) {
      OnInit -> {
        commands(FetchData)
      }
      OnAppearedOnScreen -> {
        commands(FetchStorageBackupInfo)
      }
      is ReceivedShowUsernames -> {
        news(SetShowUsernames(event.showUsernames))
      }
      is ReceivedBiometricsAvailable -> {
        state { copy(biometricsAvailable = event.available) }
      }
      is ReceivedBiometricsEnabled -> {
        state { copy(biometricsEnabled = event.enabled) }
        news(SetBiometricsEnabled(event.enabled, animate = false))
      }
      is ReceivedStorageBackupEnabled -> {
        state {
          copy(
            storageBackupEnabled = event.enabled,
            storageBackupFolderUri = event.backupFolderUri,
            storageBackupLatestDate = event.latestBackupDate
          )
        }
        news(SetStorageBackupEnabled(event.enabled))
      }
      is ReceivedImagesLoadingEnabled -> {
        state {
          copy(imagesLoadingEnabled = event.enabled)
        }
        news(SetImagesLoadingEnabled(event.enabled))
      }
      OnChangeMasterPasswordClicked -> {
        state { copy(enterPasswordDialogState = SHOWN) }
      }
      OnEnteredPasswordToChangeMasterPassword -> {
        state { copy(enterPasswordDialogState = HIDDEN_KEEPING_KEYBOARD) }
        commands(GoToMasterPasswordScreen)
      }
      OnHideEnterPasswordDialog -> {
        state { copy(enterPasswordDialogState = HIDDEN) }
      }
      OnMasterPasswordChangedReceived -> {
        if (state.biometricsEnabled) {
          state { copy(showEnableBiometricsDialog = true, biometricsEnabled = false) }
          news(SetBiometricsEnabled(enabled = false, animate = false))
          commands(DisableBiometrics)
        } else {
          news(ShowMasterPasswordChanged)
        }
      }
      is OnShowUsernamesChanged -> {
        commands(ChangeShowUsernames(event.showUsernames))
      }
      is OnEnableBiometricsChanged -> {
        if (event.enabled) {
          news(ShowBiometricsPrompt)
        } else {
          commands(DisableBiometrics)
        }
      }
      is OnBiometricsEvent -> {
        when (event.event) {
          is BiometricsEvent.Success -> {
            commands(EnableBiometrics(event.event.cryptography))
          }
          is BiometricsEvent.Error -> {
            state { copy(biometricsEnabled = false) }
            val news = buildList {
              when (event.event.error) {
                ErrorType.LOCKOUT -> add(ShowBiometricsError(LOCKOUT))
                ErrorType.LOCKOUT_PERMANENT -> add(ShowBiometricsError(LOCKOUT_PERMANENT))
                ErrorType.OTHER -> add(ShowBiometricsError(OTHER))
                else -> Unit
              }
              add(SetBiometricsEnabled(enabled = false, animate = true))
            }
            news(*news.toTypedArray())
          }
        }
      }
      OnProceedEnableBiometricsDialog -> {
        state { copy(showEnableBiometricsDialog = false) }
        news(ShowBiometricsPrompt)
      }
      OnHideEnableBiometricsDialog -> {
        state { copy(showEnableBiometricsDialog = false) }
      }
      is OnEnableStorageBackupChanged -> {
        if (event.enabled) {
          val backupFolderUri = state.storageBackupFolderUri
          if (backupFolderUri != null) {
            commands(EnableStorageBackup(backupFolderUri))
          } else {
            news(LaunchFolderSelection(initialUri = null))
          }
        } else {
          commands(DisableStorageBackup)
        }
      }
      OnSelectBackupFolderClicked -> {
        if (state.storageBackupEnabled) {
          news(LaunchFolderSelection(initialUri = state.storageBackupFolderUri))
        }
      }
      is OnSelectedBackupFolder -> {
        commands(EnableStorageBackup(event.uri))
      }
      OnBackupNowClicked -> {
        if (state.storageBackupEnabled) {
          commands(PerformBackup)
          state { copy(showLoadingBackingUp = true) }
        }
      }
      is OnEnableImagesLoadingChanged -> {
        commands(ChangeImagesLoadingEnabled(event.enabled))
      }
      OnClearImagesCacheClicked -> {
        commands(ClearImagesCache)
      }
      OnBackPressed -> {
        if (state.enterPasswordDialogState == SHOWN) {
          state { copy(enterPasswordDialogState = HIDDEN) }
        } else {
          commands(GoBack)
        }
      }
      BiometricsEnabled -> {
        state { copy(biometricsEnabled = true) }
        news(SetBiometricsEnabled(enabled = true, animate = true), ShowBiometricsEnabled)
      }
      is StorageBackupEnabled -> {
        state { copy(storageBackupEnabled = true, storageBackupFolderUri = event.backupFolderUri) }
        news(ShowStorageBackupEnabled)
      }
      StorageBackupDisabled -> {
        state { copy(storageBackupEnabled = false) }
      }
      PerformedBackup -> {
        state { copy(showLoadingBackingUp = false) }
        commands(FetchStorageBackupInfo)
        news(ShowBackupPerformed)
      }
      is ImagesLoadingEnabledChanged -> {
        state { copy(imagesLoadingEnabled = event.enabled) }
      }
      ImagesCacheCleared -> {
        news(ShowImagesCacheCleared)
      }
    }
  }
}
