package com.shashrwat.vault.features.settings.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.domain.ImagesLoadingEnabledInteractor
import com.shashrwat.vault.features.settings.SettingsCommand
import com.shashrwat.vault.features.settings.SettingsCommand.ChangeImagesLoadingEnabled
import com.shashrwat.vault.features.settings.SettingsEvent
import com.shashrwat.vault.features.settings.SettingsEvent.ImagesLoadingEnabledChanged
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

class ChangeEnableImagesLoadingActor(
  private val interactor: ImagesLoadingEnabledInteractor
) : Actor<SettingsCommand, SettingsEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<SettingsCommand>): Flow<SettingsEvent> {
    return commands.filterIsInstance<ChangeImagesLoadingEnabled>()
        .mapLatest { command ->
          interactor.setImagesLoadingEnabled(command.enabled)
          ImagesLoadingEnabledChanged(command.enabled)
        }
  }
}