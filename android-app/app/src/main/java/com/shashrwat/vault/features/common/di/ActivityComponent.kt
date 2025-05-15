package com.shashrwat.vault.features.common.di

import androidx.fragment.app.FragmentActivity
import com.shashrwat.vault.features.common.di.modules.ActivityNavigationModule
import com.shashrwat.vault.features.common.di.modules.ActivityNavigationModuleImpl

class ActivityComponent(
  private val coreComponent: CoreComponent,
  private val activityNavigationModule: ActivityNavigationModule,
) : CoreComponent by coreComponent,
  ActivityNavigationModule by activityNavigationModule {
  
  companion object {
    
    fun create(
      coreComponent: CoreComponent,
      navigationRootViewId: Int,
      activity: FragmentActivity
    ): ActivityComponent {
      val activityNavigationModule = ActivityNavigationModuleImpl(activity, navigationRootViewId)
      return ActivityComponent(coreComponent, activityNavigationModule)
    }
  }
}
