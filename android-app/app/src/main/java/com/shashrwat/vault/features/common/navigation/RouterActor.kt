package com.shashrwat.vault.features.common.navigation

import com.shashrwat.vault.core.DefaultDispatchersFacade
import com.shashrwat.vault.core.DispatchersFacade
import com.shashrwat.vault.core.extensions.emptyMap
import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.Router
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass
import kotlin.reflect.cast

inline fun <Command : Any, reified RouterCommand : Command, Event : Any> RouterActor(
  router: Router,
  dispatchersFacade: DispatchersFacade = DefaultDispatchersFacade,
  noinline action: suspend Router.(RouterCommand) -> Unit,
): Actor<Command, Event> {
  return RouterActorImpl(router, RouterCommand::class, dispatchersFacade, action)
}

class RouterActorImpl<Command : Any, RouterCommand : Command, Event : Any>(
  private val router: Router,
  private val commandToFilterClass: KClass<RouterCommand>,
  private val dispatchersFacade: DispatchersFacade,
  private val action: suspend Router.(RouterCommand) -> Unit,
) : Actor<Command, Event> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<Command>): Flow<Event> {
    return commands.filter(commandToFilterClass::isInstance)
        .emptyMap {
          withContext(dispatchersFacade.Main) {
            action(router, commandToFilterClass.cast(it))
          }
        }
  }
}
