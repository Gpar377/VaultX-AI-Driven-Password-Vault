package com.shashrwat.vault.features.login.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.biometrics.BiometricsAllowedManager
import com.shashrwat.vault.features.common.biometrics.BiometricsEnabledProvider
import com.shashrwat.vault.features.common.biometrics.BiometricsStorage
import com.shashrwat.vault.features.login.LoginCommand
import com.shashrwat.vault.features.login.LoginCommand.GetBiometricsEnterPossible
import com.shashrwat.vault.features.login.LoginEvent
import com.shashrwat.vault.features.login.LoginEvent.BiometricsEnterNotAllowed
import com.shashrwat.vault.features.login.LoginEvent.BiometricsEnterNotEnabled
import com.shashrwat.vault.features.login.LoginEvent.BiometricsEnterPossible
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

class GetBiometricsEnterPossibleActor(
  private val biometricsEnabledProvider: BiometricsEnabledProvider,
  private val biometricsAllowedManager: BiometricsAllowedManager,
  private val biometricsStorage: BiometricsStorage,
) : Actor<LoginCommand, LoginEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<LoginCommand>): Flow<LoginEvent> {
    return commands.filterIsInstance<GetBiometricsEnterPossible>()
        .mapLatest {
          if (biometricsEnabledProvider.isBiometricsEnabled()) {
            if (biometricsAllowedManager.isBiometricsEnterAllowed()) {
              val data = biometricsStorage.getBiometricsData()
              BiometricsEnterPossible(data.second)
            } else {
              BiometricsEnterNotAllowed
            }
          } else {
            BiometricsEnterNotEnabled
          }
        }
  }
}