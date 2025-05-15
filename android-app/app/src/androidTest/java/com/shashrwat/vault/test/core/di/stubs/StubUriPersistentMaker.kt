package com.shashrwat.vault.test.core.di.stubs

import android.content.Context
import android.net.Uri
import com.shashrwat.vault.features.common.data.files.UriPersistedMaker

class StubUriPersistentMaker : UriPersistedMaker {
  
  override fun takePersistableUriPermission(context: Context, uri: Uri) = Unit
}
