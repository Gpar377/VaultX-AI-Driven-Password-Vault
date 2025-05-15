package com.shashrwat.vault.test.core.ext

import app.keemobile.kotpass.database.KeePassDatabase
import app.keemobile.kotpass.database.encode
import com.shashrwat.vault.features.common.AppConstants.DEFAULT_INTERNAL_PASSWORDS_FILE_NAME
import com.shashrwat.vault.test.core.rule.VaultAutotestRule
import java.io.File
import java.io.InputStream

fun VaultAutotestRule.launchActivityWithDatabase(filename: String) {
  val bytes = context.assets.open(filename).use(InputStream::readBytes)
  val file = File(targetContext.filesDir, DEFAULT_INTERNAL_PASSWORDS_FILE_NAME)
  file.createNewFile()
  file.writeBytes(bytes)
  launchActivity()
}

fun VaultAutotestRule.launchActivityWithDatabase(database: KeePassDatabase) {
  val file = File(targetContext.filesDir, DEFAULT_INTERNAL_PASSWORDS_FILE_NAME)
  file.createNewFile()
  file.outputStream().use(database::encode)
  launchActivity()
}
