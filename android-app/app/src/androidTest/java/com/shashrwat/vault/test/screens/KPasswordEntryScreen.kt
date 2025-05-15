package com.shashrwat.vault.test.screens

import com.shashrwat.vault.features.password_entry.PasswordEntryScreen
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.ButtonSave
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.EditTextNotes
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.EditTextTitle
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.EditTextUrl
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.EditTextUsername
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.ImageBack
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.ImageCopyPassword
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.ImageDelete
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.ImageEditPassword
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.ImageFavorite
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.ImageNotesAction
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.ImageOpenUrl
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.ImageTitle
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.ImageTitleAction
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.ImageUrlAction
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.ImageUsernameAction
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.PasswordEntryScreenRoot
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.TextPassword
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.TitleNewPassword
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.TitleNotes
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.TitlePassword
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.TitleTitle
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.TitleUrl
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen.Companion.TitleUsername
import com.shashrwat.vault.test.core.base.BaseScreen
import com.shashrwat.vault.test.core.views.dialog.KInfoDialog
import com.shashrwat.vault.test.core.views.snackbar.KSnackbar
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.text.KTextView

object KPasswordEntryScreen : BaseScreen<KPasswordEntryScreen>() {
  
  override val viewClass = PasswordEntryScreen::class.java
  
  val imageBack = KImageView { withId(ImageBack) }
  val imageDelete = KImageView { withId(ImageDelete) }
  val imageFavorite = KImageView { withId(ImageFavorite) }
  val titleNewPassword = KTextView { withId(TitleNewPassword) }
  val imageTitle = KImageView { withId(ImageTitle) }
  val titleTitle = KTextView { withId(TitleTitle) }
  val editTextTitle = KEditText { withId(EditTextTitle) }
  val imageTitleAction = KImageView { withId(ImageTitleAction) }
  val titleUsername = KTextView { withId(TitleUsername) }
  val editTextUsername = KEditText { withId(EditTextUsername) }
  val imageUsernameAction = KImageView { withId(ImageUsernameAction) }
  val titlePassword = KTextView { withId(TitlePassword) }
  val textPassword = KTextView { withId(TextPassword) }
  val imageEditPassword = KImageView { withId(ImageEditPassword) }
  val imageCopyPassword = KImageView { withId(ImageCopyPassword) }
  val titleUrl = KTextView { withId(TitleUrl) }
  val editTextUrl = KEditText { withId(EditTextUrl) }
  val imageOpenUrl = KImageView { withId(ImageOpenUrl) }
  val imageUrlAction = KImageView { withId(ImageUrlAction) }
  val titleNotes = KTextView { withId(TitleNotes) }
  val editTextNotes = KEditText { withId(EditTextNotes) }
  val imageNotesAction = KImageView { withId(ImageNotesAction) }
  val buttonSave = KImageView { withId(ButtonSave) }
  val snackbar = KSnackbar()
  val confirmationDialog = KInfoDialog(PasswordEntryScreenRoot)
}
