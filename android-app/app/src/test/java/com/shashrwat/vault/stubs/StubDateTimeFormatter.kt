package com.shashrwat.vault.stubs

import com.shashrwat.vault.features.common.domain.DateTimeFormatter

class StubDateTimeFormatter : DateTimeFormatter {
  
  override fun formatSimple(timestamp: Long): String {
    return "$timestamp"
  }
  
  override fun formatReadable(timestamp: Long): String {
    return "timestamp=$timestamp"
  }
}
