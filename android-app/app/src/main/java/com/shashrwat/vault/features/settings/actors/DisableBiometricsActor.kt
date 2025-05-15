package com.shashrwat.vault.features.settings.actors

import com.shashrwat.vault.core.extensions.emptyMap
import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.biometrics.BiometricsStorage
import com.shashrwat.vault.features.settings.SettingsCommand
import com.shashrwat.vault.features.settings.SettingsCommand.DisableBiometrics
import com.shashrwat.vault.features.settings.SettingsEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance

class DisableBiometricsActor(
  private val biometricsStorage: BiometricsStorage
) : Actor<SettingsCommand, SettingsEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<SettingsCommand>): Flow<SettingsEvent> {
    return commands.filterIsInstance<DisableBiometrics>()
        .emptyMap { biometricsStorage.clear() }
  }
}