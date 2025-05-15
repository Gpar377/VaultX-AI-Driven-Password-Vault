package com.shashrwat.vault.features.master_password.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.master_password.MasterPasswordCommand
import com.shashrwat.vault.features.master_password.MasterPasswordEvent
import com.shashrwat.vault.features.master_password.MasterPasswordEvent.UpdatedPasswordStrength
import domain.PasswordChecker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

class CheckPasswordStrengthActor(
  private val passwordChecker: PasswordChecker,
) : Actor<MasterPasswordCommand, MasterPasswordEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<MasterPasswordCommand>): Flow<MasterPasswordEvent> {
    return commands.filterIsInstance<MasterPasswordCommand.PasswordCommand.CheckPasswordStrength>()
        .mapLatest { command ->
          val passwordStrength = passwordChecker.checkStrength(command.password)
          UpdatedPasswordStrength(passwordStrength)
        }
  }
}
