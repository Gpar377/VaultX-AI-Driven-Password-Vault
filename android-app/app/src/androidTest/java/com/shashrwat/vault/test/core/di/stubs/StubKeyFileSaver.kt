package com.shashrwat.vault.test.core.di.stubs

import com.shashrwat.vault.features.common.data.files.KeyFileSaver

class StubKeyFileSaver : KeyFileSaver {
  
  private var keyFileData: ByteArray? = null
  
  override suspend fun saveKeyFile(bytes: ByteArray) {
    keyFileData = bytes
  }
  
  override suspend fun getKeyFileIfExists(): ByteArray? {
    return keyFileData
  }
}
