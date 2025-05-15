package com.shashrwat.vault.core.extensions

import android.text.SpannableString
import com.shashrwat.vault.R
import com.shashrwat.vault.core.TypefaceSpan
import com.shashrwat.vault.viewbuilding.Fonts
import navigation.BaseFragmentScreen

fun BaseFragmentScreen.getDeleteMessageText(name: String): CharSequence {
  val questionPrefixLength = requireContext().getString(
    R.string.text_delete_password_question_prefix
  ).length
  val questionString =
      requireContext().getString(R.string.text_delete_password_question, name)
  val spannableString = SpannableString(questionString)
  spannableString.setSpan(
    TypefaceSpan(Fonts.SegoeUiBold), questionPrefixLength + 1,
    questionString.length - 1, 0
  )
  return spannableString
}
