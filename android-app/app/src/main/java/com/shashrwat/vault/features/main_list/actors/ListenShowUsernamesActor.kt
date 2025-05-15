package com.shashrwat.vault.features.main_list.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.domain.ShowUsernamesInteractor
import com.shashrwat.vault.features.main_list.MainListCommand
import com.shashrwat.vault.features.main_list.MainListEvent
import com.shashrwat.vault.features.main_list.MainListEvent.NotifyShowUsernamesChanged
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class ListenShowUsernamesActor(
  private val showUsernamesInteractor: ShowUsernamesInteractor,
) : Actor<MainListCommand, MainListEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<MainListCommand>): Flow<MainListEvent> {
    return showUsernamesInteractor.showUsernamesFlow
        .mapLatest { NotifyShowUsernamesChanged }
  }
}