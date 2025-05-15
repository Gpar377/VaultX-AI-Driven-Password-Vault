package com.shashrwat.vault.features.settings.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.biometrics.BiometricsAllowedManager
import com.shashrwat.vault.features.common.biometrics.BiometricsStorage
import com.shashrwat.vault.features.common.domain.MasterPasswordProvider
import com.shashrwat.vault.features.settings.SettingsCommand
import com.shashrwat.vault.features.settings.SettingsCommand.EnableBiometrics
import com.shashrwat.vault.features.settings.SettingsEvent
import com.shashrwat.vault.features.settings.SettingsEvent.BiometricsEnabled
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

class EnableBiometricsActor(
  private val masterPasswordProvider: MasterPasswordProvider,
  private val biometricsAllowedManager: BiometricsAllowedManager,
  private val biometricsStorage: BiometricsStorage
) : Actor<SettingsCommand, SettingsEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<SettingsCommand>): Flow<SettingsEvent> {
    return commands.filterIsInstance<EnableBiometrics>()
        .mapLatest { command ->
          val masterPassword = masterPasswordProvider.provideMasterPassword()
          val passwordBytes = masterPassword.encryptedValueField.getBinary()
          val encryptedBytes = command.cryptography.perform(passwordBytes)
          biometricsStorage.saveBiometricsData(encryptedBytes, command.cryptography.iv)
          biometricsAllowedManager.resetBiometricsStats()
          BiometricsEnabled
        }
  }
  
}
