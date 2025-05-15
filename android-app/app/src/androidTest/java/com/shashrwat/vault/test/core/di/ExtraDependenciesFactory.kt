package com.shashrwat.vault.test.core.di

import com.shashrwat.vault.features.common.di.ExtraDependencies
import com.shashrwat.vault.features.common.di.ExtraDependenciesFactory
import com.shashrwat.vault.test.core.di.stubs.StubActivityResultWrapper
import com.shashrwat.vault.test.core.di.stubs.StubExternalFileReader
import com.shashrwat.vault.test.core.di.stubs.StubInterceptor
import com.shashrwat.vault.test.core.di.stubs.StubKeyFileSaver
import com.shashrwat.vault.test.core.di.stubs.StubPasswordsFileExporter
import com.shashrwat.vault.test.core.di.stubs.StubUriPersistentMaker
import com.shashrwat.vault.test.core.di.stubs.TestBackupInterceptor
import com.shashrwat.vault.test.core.di.stubs.TestImageRequestsRecorder
import com.shashrwat.vault.test.core.ext.context
import okhttp3.OkHttpClient

fun StubExtraDependenciesFactory(
  activityResultWrapper: StubActivityResultWrapper = StubActivityResultWrapper(),
  passwordsFileExporter: StubPasswordsFileExporter = StubPasswordsFileExporter(),
  externalFileReader: StubExternalFileReader = StubExternalFileReader(),
  stubKeyFileSaver: StubKeyFileSaver = StubKeyFileSaver(),
  imagesRequestsRecorder: TestImageRequestsRecorder = TestImageRequestsRecorder(),
  backupInterceptor: TestBackupInterceptor = TestBackupInterceptor(),
): ExtraDependenciesFactory {
  return object : ExtraDependenciesFactory {
    override fun getExtraDependencies(): ExtraDependencies {
      return object : ExtraDependencies {
        override val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(StubInterceptor(context))
            .build()
        override val activityResultWrapper = activityResultWrapper
        override val passwordsFileExporter = passwordsFileExporter
        override val externalFileReader = externalFileReader
        override val keyFileSaver = stubKeyFileSaver
        override val imageRequestsRecorder = imagesRequestsRecorder
        override val backupInterceptor = backupInterceptor
        override val uriPersistedMaker = StubUriPersistentMaker()
      }
    }
  }
}