package com.shashrwat.vault.features.common.extensions

import com.shashrwat.vault.core.mvi.tea.DslReducer
import com.shashrwat.vault.features.common.TextState
import com.shashrwat.vault.features.common.reset
import com.shashrwat.vault.features.common.update
import domain.model.BaseEntry

fun <Item : BaseEntry, State : Any, Command : Any,
    News : Any> DslReducer<State, *, Command, News>.handleAction(
  itemProvider: () -> Item,
  textState: TextState,
  updateTextAction: (TextState) -> State,
  updateAction: Item.(String) -> Item,
  updateCommand: (Item) -> Command,
  allowEmptySave: Boolean = false,
  showErrorIsEmptyAction: (State.() -> State)? = null,
  copyCommand: (text: String) -> Command,
  copyNews: News
) {
  with(textState) {
    if (isEditingNow) {
      if (!allowEmptySave && editedText.isBlank()) {
        showErrorIsEmptyAction?.invoke(state)?.let { newState -> state { newState } }
        return
      }
      val trimmedText = editedText.trim()
      if (trimmedText == initialText) {
        state { updateTextAction(textState.reset()) }
      } else {
        state { updateTextAction(textState.update(trimmedText)) }
        commands(updateCommand(updateAction(itemProvider(), trimmedText)))
      }
    } else {
      commands(copyCommand(editedText))
      news(copyNews)
    }
  }
}