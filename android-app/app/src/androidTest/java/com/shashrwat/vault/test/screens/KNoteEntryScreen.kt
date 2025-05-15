package com.shashrwat.vault.test.screens

import com.shashrwat.vault.R
import com.shashrwat.vault.features.note_entry.NoteEntryScreen
import com.shashrwat.vault.features.note_entry.NoteEntryScreen.Companion.ButtonSave
import com.shashrwat.vault.features.note_entry.NoteEntryScreen.Companion.EditTextText
import com.shashrwat.vault.features.note_entry.NoteEntryScreen.Companion.EditTextTitle
import com.shashrwat.vault.features.note_entry.NoteEntryScreen.Companion.ImageDelete
import com.shashrwat.vault.features.note_entry.NoteEntryScreen.Companion.ImageTextAction
import com.shashrwat.vault.features.note_entry.NoteEntryScreen.Companion.ImageTitleAction
import com.shashrwat.vault.features.note_entry.NoteEntryScreen.Companion.MainTitle
import com.shashrwat.vault.features.note_entry.NoteEntryScreen.Companion.NoteScreenRoot
import com.shashrwat.vault.features.note_entry.NoteEntryScreen.Companion.Title
import com.shashrwat.vault.features.note_entry.NoteEntryScreen.Companion.TitleText
import com.shashrwat.vault.test.core.base.BaseScreen
import com.shashrwat.vault.test.core.views.dialog.KInfoDialog
import com.shashrwat.vault.test.core.views.snackbar.KSnackbar
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.text.KTextView

object KNoteEntryScreen : BaseScreen<KNoteEntryScreen>() {
  
  override val viewClass = NoteEntryScreen::class.java
  
  val imageBack = KImageView { withDrawable(R.drawable.ic_back) }
  val textMainTitle = KTextView { withId(MainTitle) }
  val imageDelete = KImageView { withId(ImageDelete) }
  val title = KTextView { withId(Title) }
  val editTextTitle = KEditText { withId(EditTextTitle) }
  val imageTitleAction = KImageView { withId(ImageTitleAction) }
  val titleText = KTextView { withId(TitleText) }
  val editTextText = KEditText { withId(EditTextText) }
  val imageTextAction = KImageView { withId(ImageTextAction) }
  val buttonSave = KTextView { withId(ButtonSave) }
  val confirmationDialog = KInfoDialog(NoteScreenRoot)
  val snackbar = KSnackbar()
}
