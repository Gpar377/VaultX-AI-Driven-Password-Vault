package com.shashrwat.vault.features.main_list.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.data.network.NetworkAvailabilityProvider
import com.shashrwat.vault.features.main_list.MainListCommand
import com.shashrwat.vault.features.main_list.MainListEvent
import com.shashrwat.vault.features.main_list.MainListEvent.NetworkAvailable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class ListenNetworkAvailabilityActor(
  private val networkAvailabilityProvider: NetworkAvailabilityProvider
) : Actor<MainListCommand, MainListEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<MainListCommand>): Flow<MainListEvent> {
    return networkAvailabilityProvider.availabilityFlow
        .mapLatest { NetworkAvailable }
  }
}