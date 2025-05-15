package com.shashrwat.vault.features.main_list.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.domain.MasterPasswordProvider
import com.shashrwat.vault.features.main_list.MainListCommand
import com.shashrwat.vault.features.main_list.MainListCommand.FilterEntries
import com.shashrwat.vault.features.main_list.MainListEvent
import com.shashrwat.vault.features.main_list.MainListEvent.MasterPasswordNull
import com.shashrwat.vault.features.main_list.MainListEvent.UpdateSearchResult
import com.shashrwat.vault.features.main_list.domain.LoadEntriesInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

class SearchEntriesActor(
  private val masterPasswordProvider: MasterPasswordProvider,
  private val interactor: LoadEntriesInteractor,
) : Actor<MainListCommand, MainListEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<MainListCommand>): Flow<MainListEvent> {
    return commands.filterIsInstance<FilterEntries>()
        .mapLatest { command ->
          val masterPassword = masterPasswordProvider.provideMasterPasswordIfSet()
              ?: return@mapLatest MasterPasswordNull
          val state = interactor.loadEntries(masterPassword, filterQuery = command.searchText)
          UpdateSearchResult(state)
        }
  }
}
