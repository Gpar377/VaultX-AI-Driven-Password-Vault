package com.shashrwat.vault.core.communicators

import kotlinx.coroutines.flow.MutableSharedFlow

interface Communicator<Input, Output> {
  val input: MutableSharedFlow<Input>
  val output: MutableSharedFlow<Output>
}
