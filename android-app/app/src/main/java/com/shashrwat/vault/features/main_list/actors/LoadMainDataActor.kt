package com.shashrwat.vault.features.main_list.actors

import com.shashrwat.vault.core.ListScreenState
import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.domain.MasterPasswordProvider
import com.shashrwat.vault.features.main_list.MainListCommand
import com.shashrwat.vault.features.main_list.MainListCommand.LoadData
import com.shashrwat.vault.features.main_list.MainListEvent
import com.shashrwat.vault.features.main_list.MainListEvent.MasterPasswordNull
import com.shashrwat.vault.features.main_list.MainListEvent.UpdateData
import com.shashrwat.vault.features.main_list.domain.LoadEntriesInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

class LoadMainDataActor(
  private val masterPasswordProvider: MasterPasswordProvider,
  private val loadEntriesInteractor: LoadEntriesInteractor,
) : Actor<MainListCommand, MainListEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<MainListCommand>): Flow<MainListEvent> {
    return commands.filterIsInstance<LoadData>()
        .mapLatest {
          val masterPassword = masterPasswordProvider.provideMasterPasswordIfSet()
              ?: return@mapLatest MasterPasswordNull
          val entries = loadEntriesInteractor.loadEntries(masterPassword, filterQuery = "")
          val state = if (entries.isNotEmpty()) {
            ListScreenState.success(entries)
          } else {
            ListScreenState.empty()
          }
          UpdateData(state)
        }
  }
}
