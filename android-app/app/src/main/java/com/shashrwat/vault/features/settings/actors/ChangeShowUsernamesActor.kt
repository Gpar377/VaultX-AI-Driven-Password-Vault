package com.shashrwat.vault.features.settings.actors

import com.shashrwat.vault.core.extensions.emptyMap
import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.domain.ShowUsernamesInteractor
import com.shashrwat.vault.features.settings.SettingsCommand
import com.shashrwat.vault.features.settings.SettingsCommand.ChangeShowUsernames
import com.shashrwat.vault.features.settings.SettingsEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance

class ChangeShowUsernamesActor(
  private val interactor: ShowUsernamesInteractor
) : Actor<SettingsCommand, SettingsEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<SettingsCommand>): Flow<SettingsEvent> {
    return commands.filterIsInstance<ChangeShowUsernames>()
        .emptyMap { command ->
          interactor.setShowUsernames(command.show)
        }
  }
}
