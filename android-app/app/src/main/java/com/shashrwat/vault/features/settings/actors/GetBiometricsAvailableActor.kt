package com.shashrwat.vault.features.settings.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.biometrics.BiometricsAvailabilityProvider
import com.shashrwat.vault.features.settings.SettingsCommand
import com.shashrwat.vault.features.settings.SettingsCommand.FetchData
import com.shashrwat.vault.features.settings.SettingsEvent
import com.shashrwat.vault.features.settings.SettingsEvent.ReceivedBiometricsAvailable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

class GetBiometricsAvailableActor(
  private val biometricsAvailabilityProvider: BiometricsAvailabilityProvider
) : Actor<SettingsCommand, SettingsEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<SettingsCommand>): Flow<SettingsEvent> {
    return commands.filterIsInstance<FetchData>()
        .mapLatest {
          val available = biometricsAvailabilityProvider.isAvailable()
          ReceivedBiometricsAvailable(available)
        }
  }
}
