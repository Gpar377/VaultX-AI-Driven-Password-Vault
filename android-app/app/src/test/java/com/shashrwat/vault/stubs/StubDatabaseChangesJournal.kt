package com.shashrwat.vault.stubs

import com.shashrwat.vault.features.common.domain.DatabaseChangesJournal

class StubDatabaseChangesJournal : DatabaseChangesJournal {
  
  private var changes = 0
  
  override suspend fun markChange() {
    changes++
  }
  
  override suspend fun getChangeCount(): Int {
    return changes
  }
}
