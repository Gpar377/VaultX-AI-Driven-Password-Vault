package com.shashrwat.vault.features.main_list.domain

import com.shashrwat.vault.features.common.data.database.ObservableCachedDatabaseStorage
import com.shashrwat.vault.features.common.domain.ShowUsernamesInteractor
import com.shashrwat.vault.recycler.DifferentiableItem
import domain.Password

class LoadEntriesInteractor(
  private val entriesStorage: ObservableCachedDatabaseStorage,
  private val entriesListUiMapper: EntriesListUiMapper,
  private val showUsernamesInteractor: ShowUsernamesInteractor,
) {
  
  suspend fun loadEntries(
    masterPassword: Password,
    filterQuery: String
  ): List<DifferentiableItem> {
    val showUsernames = showUsernamesInteractor.getShowUsernames()
    val database = entriesStorage.getDatabase(masterPassword)
    return entriesListUiMapper.mapItems(database, showUsernames, filterQuery)
  }
}
