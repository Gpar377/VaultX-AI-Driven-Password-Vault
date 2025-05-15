package com.shashrwat.vault.features.common.di.modules

import com.shashrwat.vault.features.common.Router
import com.shashrwat.vault.features.common.navigation.result_contracts.ActivityResultWrapper
import navigation.NavigationController

interface NavigationModule {
  val router: Router
  val navigationController: NavigationController
  val activityResultWrapper: ActivityResultWrapper
}

class NavigationModuleImpl(
  override val activityResultWrapper: ActivityResultWrapper
) : NavigationModule {
  
  override val router = Router()
  
  override val navigationController = NavigationController.create(router)
}
