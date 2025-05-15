package com.shashrwat.vault.features.common

import android.net.Uri
import com.shashrwat.vault.core.extensions.bundleOf
import com.shashrwat.vault.features.common.model.PasswordItem
import com.shashrwat.vault.features.common.model.NoteItem
import com.shashrwat.vault.features.creating_password.CreatingPasswordScreen
import com.shashrwat.vault.features.import_passwords.ImportPasswordsScreen
import com.shashrwat.vault.features.import_passwords.ImportPasswordsScreen.Companion.ASK_FOR_CONFIRMATION
import com.shashrwat.vault.features.initial.InitialScreen
import com.shashrwat.vault.features.login.LoginScreen
import com.shashrwat.vault.features.main_list.MainListScreen
import com.shashrwat.vault.features.master_password.MasterPasswordScreen
import com.shashrwat.vault.features.master_password.MasterPasswordScreenMode
import com.shashrwat.vault.features.password_entry.PasswordEntryScreen
import com.shashrwat.vault.features.note_entry.NoteEntryScreen
import com.shashrwat.vault.features.settings.SettingsScreen
import navigation.Screen
import navigation.ScreenInfo

object Screens {
  
  val InitialScreen = Screen { InitialScreen::class }
  
  fun MasterPasswordScreen(mode: MasterPasswordScreenMode): ScreenInfo {
    val pair = MasterPasswordScreenMode::class.java.name to mode
    return Screen(arguments = bundleOf(pair)) { MasterPasswordScreen::class }
  }
  
  val LoginScreen = Screen { LoginScreen::class }
  
  val MainListScreen = Screen { MainListScreen::class }
  
  fun PasswordEntryScreen(passwordId: String? = null): ScreenInfo {
    val pair = PasswordItem::class.java.name to passwordId
    return Screen(arguments = bundleOf(pair)) { PasswordEntryScreen::class }
  }
  
  val CreatingPasswordScreen = Screen { CreatingPasswordScreen::class }
  
  fun NoteScreen(noteId: String? = null): ScreenInfo {
    val pair = NoteItem::class.java.name to noteId
    return Screen(arguments = bundleOf(pair)) { NoteEntryScreen::class }
  }
  
  val SettingsScreen = Screen { SettingsScreen::class }
  
  fun ImportPasswordsScreen(
    selectedFileUri: Uri? = null,
    askForConfirmation: Boolean = true
  ): ScreenInfo {
    val uri = Uri::class.java.name to selectedFileUri
    val confirmationFlag = ASK_FOR_CONFIRMATION to askForConfirmation
    return Screen(arguments = bundleOf(uri, confirmationFlag)) { ImportPasswordsScreen::class }
  }
}
