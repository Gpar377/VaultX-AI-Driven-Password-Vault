package com.shashrwat.vault.features.password_entry.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.data.network.NetworkAvailabilityProvider
import com.shashrwat.vault.features.password_entry.PasswordEntryCommand
import com.shashrwat.vault.features.password_entry.PasswordEntryEvent
import com.shashrwat.vault.features.password_entry.PasswordEntryEvent.NetworkAvailable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class ListenNetworkAvailabilityActor(
  private val networkAvailabilityProvider: NetworkAvailabilityProvider
) : Actor<PasswordEntryCommand, PasswordEntryEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<PasswordEntryCommand>): Flow<PasswordEntryEvent> {
    return networkAvailabilityProvider.availabilityFlow
        .mapLatest { NetworkAvailable }
  }
}