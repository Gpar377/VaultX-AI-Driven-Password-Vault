package com.shashrwat.vault.core.mvi.tea

import kotlinx.coroutines.flow.Flow

interface Actor<Command, Event> {
  
  fun handle(commands: Flow<Command>): Flow<Event>
}
