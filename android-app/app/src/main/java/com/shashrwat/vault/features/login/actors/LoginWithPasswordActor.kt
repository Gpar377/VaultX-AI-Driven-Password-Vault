package com.shashrwat.vault.features.login.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.biometrics.BiometricsAllowedManager
import com.shashrwat.vault.features.login.LoginCommand
import com.shashrwat.vault.features.login.LoginCommand.EnterWithMasterPassword
import com.shashrwat.vault.features.login.LoginEvent
import com.shashrwat.vault.features.login.LoginEvent.ShowFailureCheckingPassword
import com.shashrwat.vault.features.login.LoginEvent.ShowLoginSuccess
import domain.MasterPasswordChecker
import domain.MasterPasswordHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

class LoginWithPasswordActor(
  private val masterPasswordChecker: MasterPasswordChecker,
  private val biometricsAllowedManager: BiometricsAllowedManager
) : Actor<LoginCommand, LoginEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<LoginCommand>): Flow<LoginEvent> {
    return commands.filterIsInstance<EnterWithMasterPassword>()
        .mapLatest { command ->
          if (masterPasswordChecker.isCorrect(command.password)) {
            MasterPasswordHolder.setMasterPassword(command.password)
            biometricsAllowedManager.resetBiometricsStats()
            ShowLoginSuccess
          } else {
            ShowFailureCheckingPassword
          }
        }
  }
}
