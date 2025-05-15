package com.shashrwat.vault.test.screens

import com.shashrwat.vault.R
import com.shashrwat.vault.core.views.TextWithQuestion
import com.shashrwat.vault.features.common.dialogs.DialogProgressBar
import com.shashrwat.vault.features.master_password.MasterPasswordScreen
import com.shashrwat.vault.features.master_password.MasterPasswordScreen.Companion.EditTextEnterPassword
import com.shashrwat.vault.features.master_password.MasterPasswordScreen.Companion.EditTextRepeatPassword
import com.shashrwat.vault.features.master_password.MasterPasswordScreen.Companion.MasterPasswordScreenRoot
import com.shashrwat.vault.features.master_password.MasterPasswordScreen.Companion.TextContinue
import com.shashrwat.vault.features.master_password.MasterPasswordScreen.Companion.TextPasswordStrength
import com.shashrwat.vault.features.master_password.MasterPasswordScreen.Companion.TitleFirst
import com.shashrwat.vault.features.master_password.MasterPasswordScreen.Companion.TitleSecond
import com.shashrwat.vault.test.core.base.BaseScreen
import com.shashrwat.vault.test.core.views.dialog.KInfoDialog
import com.shashrwat.vault.test.core.views.dialog.KPasswordStrengthDialog
import com.shashrwat.vault.test.core.views.edit_text_password.KEditTextPassword
import com.shashrwat.vault.test.core.views.password_strength_meter.KPasswordStrengthMeter
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.text.KTextView

object KMasterPasswordScreen : BaseScreen<KMasterPasswordScreen>() {
  
  override val viewClass = MasterPasswordScreen::class.java
  
  val imageBack = KImageView { withDrawable(R.drawable.ic_back) }
  val titleFirst = KTextView { withId(TitleFirst) }
  val titleSecond = KTextView { withId(TitleSecond) }
  val editTextEnterPassword = KEditTextPassword { withId(EditTextEnterPassword) }
  val editTextRepeatPassword = KEditTextPassword { withId(EditTextRepeatPassword) }
  val textPasswordStrength = KTextView { withId(TextPasswordStrength) }
  val passwordStrengthMeter = KPasswordStrengthMeter()
  val textError = KTextView { withId(TextWithQuestion.Text) }
  val iconError = KImageView { withId(TextWithQuestion.Image) }
  val buttonContinue = KTextView {
    withId(TextContinue)
    withText("Continue")
  }
  val passwordStrengthDialog = KPasswordStrengthDialog()
  val confirmationDialog = KInfoDialog(MasterPasswordScreenRoot)
  val loadingDialog = KView {
    isDescendantOfA { withId(MasterPasswordScreenRoot) }
    withTag(DialogProgressBar)
  }
}
