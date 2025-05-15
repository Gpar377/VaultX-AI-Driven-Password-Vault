package com.shashrwat.vault.test.core.di.stubs

import android.graphics.drawable.Drawable
import com.shashrwat.vault.features.common.data.images.ImagesCache

class DisabledImagesCache : ImagesCache {
  
  override suspend fun getImage(key: String) = null
  
  override suspend fun saveImage(key: String, drawable: Drawable) = Unit
  
  override suspend fun clearAll() = Unit
}
