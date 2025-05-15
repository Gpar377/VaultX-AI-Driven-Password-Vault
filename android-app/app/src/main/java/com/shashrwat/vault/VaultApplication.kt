package com.shashrwat.vault

import android.app.Application
import com.shashrwat.vault.core.DefaultDispatchersFacade
import com.shashrwat.vault.features.common.di.CoreComponentHolder
import com.shashrwat.vault.features.common.di.RealExtraDependenciesFactory
import com.shashrwat.vault.viewbuilding.Fonts
import com.shashrwat.vault.viewbuilding.Styles
import timber.log.Timber
import viewdsl.ViewDslConfiguration

class VaultApplication : Application() {
  
  override fun onCreate() {
    super.onCreate()
    Timber.plant(Timber.DebugTree())
    ViewDslConfiguration.initializeWithAppContext(this)
    ViewDslConfiguration.setCoreStyles(Styles)
    Fonts.init(applicationContext)
    val extraDependenciesFactory = RealExtraDependenciesFactory(this, DefaultDispatchersFacade)
    CoreComponentHolder.initialize(this, extraDependenciesFactory)
  }
}
