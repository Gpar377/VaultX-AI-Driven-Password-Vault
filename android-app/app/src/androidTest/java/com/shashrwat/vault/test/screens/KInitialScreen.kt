package com.shashrwat.vault.test.screens

import com.shashrwat.vault.features.initial.InitialScreen
import com.shashrwat.vault.features.initial.InitialScreen.Companion.ButtonCreateMasterPassword
import com.shashrwat.vault.features.initial.InitialScreen.Companion.ButtonImportPasswords
import com.shashrwat.vault.test.core.base.BaseScreen
import io.github.kakaocup.kakao.common.views.KView

object KInitialScreen : BaseScreen<KInitialScreen>() {
  
  override val viewClass = InitialScreen::class.java
  
  val buttonCreateMasterPassword = KView { withId(ButtonCreateMasterPassword) }
  val buttonImportPasswords = KView { withId(ButtonImportPasswords) }
}
