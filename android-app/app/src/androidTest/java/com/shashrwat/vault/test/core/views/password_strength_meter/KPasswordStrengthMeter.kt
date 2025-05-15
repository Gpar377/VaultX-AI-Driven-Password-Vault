package com.shashrwat.vault.test.core.views.password_strength_meter

import com.shashrwat.vault.core.views.PasswordStrengthMeter
import com.shashrwat.vault.test.core.views.edit_text_password.KEditTextPassword
import io.github.kakaocup.kakao.common.views.KBaseView

class KPasswordStrengthMeter : KBaseView<KEditTextPassword>({
  isInstanceOf(PasswordStrengthMeter::class.java)
}), KPasswordStrengthAssertions
