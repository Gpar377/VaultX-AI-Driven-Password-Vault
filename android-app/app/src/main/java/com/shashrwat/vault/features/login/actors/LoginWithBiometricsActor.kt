package com.shashrwat.vault.features.login.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.biometrics.BiometricsAllowedManager
import com.shashrwat.vault.features.common.biometrics.BiometricsStorage
import com.shashrwat.vault.features.login.LoginCommand
import com.shashrwat.vault.features.login.LoginCommand.EnterWithBiometrics
import com.shashrwat.vault.features.login.LoginEvent
import com.shashrwat.vault.features.login.LoginEvent.ShowFailureCheckingBiometrics
import com.shashrwat.vault.features.login.LoginEvent.ShowLoginSuccess
import domain.MasterPasswordChecker
import domain.MasterPasswordHolder
import domain.Password
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

class LoginWithBiometricsActor(
  private val biometricsStorage: BiometricsStorage,
  private val biometricsAllowedManager: BiometricsAllowedManager,
  private val masterPasswordChecker: MasterPasswordChecker,
) : Actor<LoginCommand, LoginEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<LoginCommand>): Flow<LoginEvent> {
    return commands.filterIsInstance<EnterWithBiometrics>()
        .mapLatest { command ->
          val encryptedPassword = biometricsStorage.getBiometricsData().first
          val bytes = command.cryptography.perform(encryptedPassword)
          val password = Password.fromRaw(bytes)
          if (masterPasswordChecker.isCorrect(password)) {
            MasterPasswordHolder.setMasterPassword(password)
            biometricsAllowedManager.markBiometricsEnter()
            ShowLoginSuccess
          } else {
            ShowFailureCheckingBiometrics
          }
        }
  }
}
