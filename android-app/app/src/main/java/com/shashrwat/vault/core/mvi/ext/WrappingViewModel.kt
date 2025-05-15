package com.shashrwat.vault.core.mvi.ext

import androidx.lifecycle.ViewModel
import com.shashrwat.vault.core.DispatchersFacade
import com.shashrwat.vault.core.mvi.tea.TeaStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

internal class ViewModelWithStore<S : TeaStore<*, *, *>>(
  val store: S,
  dispatchersFacade: DispatchersFacade,
) : ViewModel() {
  
  private val viewModelScope = CoroutineScope(dispatchersFacade.Main + SupervisorJob())
  
  init {
    store.launch(viewModelScope, dispatchersFacade)
  }
  
  override fun onCleared() {
    viewModelScope.cancel()
  }
}
