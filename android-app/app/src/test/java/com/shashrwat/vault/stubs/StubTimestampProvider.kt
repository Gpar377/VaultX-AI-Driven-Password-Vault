package com.shashrwat.vault.stubs

import com.shashrwat.vault.features.common.domain.TimestampProvider

class StubTimestampProvider : TimestampProvider {
  
  var now = 0L
  
  override fun now() = now
}
