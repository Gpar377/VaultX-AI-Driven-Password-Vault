package com.shashrwat.vault.test.tests

import androidx.test.core.app.ApplicationProvider
import com.shashrwat.vault.core.views.drawables.LetterInCircleDrawable
import com.shashrwat.vault.features.common.di.CoreComponentHolder
import com.shashrwat.vault.features.main_list.MainListScreen
import com.shashrwat.vault.features.settings.SettingsScreen
import com.shashrwat.vault.test.core.base.VaultTestCase
import com.shashrwat.vault.test.core.data.Databases
import com.shashrwat.vault.test.core.di.StubExtraDependenciesFactory
import com.shashrwat.vault.test.core.di.stubs.TestImageRequestsRecorder
import com.shashrwat.vault.test.core.di.stubs.URL_IMAGE_GOOGLE
import com.shashrwat.vault.test.core.ext.currentScreenIs
import com.shashrwat.vault.test.core.ext.launchActivityWithDatabase
import com.shashrwat.vault.test.core.ext.wasImageRequestWithUrlPerformed
import com.shashrwat.vault.test.core.ext.wasNoImageRequestPerformed
import com.shashrwat.vault.test.core.rule.VaultAutotestRule
import com.shashrwat.vault.test.screens.KLoginScreen
import com.shashrwat.vault.test.screens.KMainListScreen
import com.shashrwat.vault.test.screens.KMainListScreen.PasswordItem
import com.shashrwat.vault.test.screens.KSettingsScreen
import org.junit.Rule
import org.junit.Test

class SettingsTest : VaultTestCase() {
  
  @get:Rule
  val rule = VaultAutotestRule()
  
  private val testImageRequestsRecorder = TestImageRequestsRecorder()
  
  @Test
  fun testSettingsScreenFlow() = init {
    rule.launchActivityWithDatabase(Databases.Empty)
  }.run {
    KLoginScreen {
      editTextEnterPassword.replaceText("qwetu1233")
      buttonContinue.click()
      KMainListScreen {
        menu {
          open()
          settingsMenuItem.click()
        }
        KSettingsScreen {
          itemChangePassword.click()
          closeSoftKeyboard()
          pressBack()
          
          currentScreenIs<SettingsScreen>()
          
          imageBack.click()
        }
        
        currentScreenIs<MainListScreen>()
        
        menu {
          open()
          settingsMenuItem.click()
        }
        KSettingsScreen {
          itemChangePassword.click()
          
          enterPasswordDialog.imageCross.click()
          
          currentScreenIs<SettingsScreen>()
        }
      }
    }
  }
  
  @Test
  fun testImagesLoading() = init {
    CoreComponentHolder.initialize(
      application = ApplicationProvider.getApplicationContext(),
      factory = StubExtraDependenciesFactory(
        imagesRequestsRecorder = testImageRequestsRecorder
      )
    )
    rule.launchActivityWithDatabase(Databases.TwoPasswords)
  }.run {
    KLoginScreen {
      editTextEnterPassword.replaceText("qwetu1233")
      buttonContinue.click()
      KMainListScreen {
        recycler {
          childAt<PasswordItem>(1) {
            image.wasImageRequestWithUrlPerformed(URL_IMAGE_GOOGLE, testImageRequestsRecorder)
          }
        }
        menu {
          open()
          settingsMenuItem.click()
        }
        KSettingsScreen {
          switchImagesLoading.isEnabled()
          
          switchImagesLoading.click()
          
          pressBack()
        }
        
        recycler {
          childAt<PasswordItem>(1) {
            image.wasNoImageRequestPerformed(testImageRequestsRecorder)
            image.hasDrawable(LetterInCircleDrawable("g"))
          }
        }
      }
    }
    
    rule.finishActivity()
    rule.launchActivity()
    
    KLoginScreen {
      editTextEnterPassword.replaceText("qwetu1233")
      buttonContinue.click()
      KMainListScreen {
        recycler {
          childAt<PasswordItem>(1) {
            image.wasNoImageRequestPerformed(testImageRequestsRecorder)
            image.hasDrawable(LetterInCircleDrawable("g"))
          }
        }
        menu {
          open()
          settingsMenuItem.click()
        }
        KSettingsScreen {
          switchImagesLoading.click()
          pressBack()
        }
        recycler {
          childAt<PasswordItem>(1) {
            image.wasImageRequestWithUrlPerformed(URL_IMAGE_GOOGLE, testImageRequestsRecorder)
          }
        }
      }
    }
  }
}
