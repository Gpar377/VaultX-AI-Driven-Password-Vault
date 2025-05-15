package com.shashrwat.vault.features.creating_password.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.creating_password.CreatingPasswordCommand
import com.shashrwat.vault.features.creating_password.CreatingPasswordCommand.ComputePasswordCharacteristics
import com.shashrwat.vault.features.creating_password.CreatingPasswordEvent
import com.shashrwat.vault.features.creating_password.CreatingPasswordEvent.ComputedPasswordCharacteristics
import domain.PasswordCharacteristicsProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

class ComputePasswordCharacteristicsActor(
  private val passwordCharacteristicsProvider: PasswordCharacteristicsProvider
) : Actor<CreatingPasswordCommand, CreatingPasswordEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<CreatingPasswordCommand>): Flow<CreatingPasswordEvent> {
    return commands.filterIsInstance<ComputePasswordCharacteristics>()
        .mapLatest { command ->
          val characteristics = passwordCharacteristicsProvider.getCharacteristics(command.password)
          ComputedPasswordCharacteristics(characteristics)
        }
  }
}
