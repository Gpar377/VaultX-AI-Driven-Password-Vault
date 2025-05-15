package com.shashrwat.vault.features.login

import com.shashrwat.vault.core.mvi.tea.DslReducer
import com.shashrwat.vault.features.common.biometrics.BiometricsEvent.Error
import com.shashrwat.vault.features.common.biometrics.BiometricsEvent.ErrorType.CANCELLED
import com.shashrwat.vault.features.common.biometrics.BiometricsEvent.ErrorType.LOCKOUT
import com.shashrwat.vault.features.common.biometrics.BiometricsEvent.ErrorType.LOCKOUT_PERMANENT
import com.shashrwat.vault.features.common.biometrics.BiometricsEvent.ErrorType.OTHER
import com.shashrwat.vault.features.common.biometrics.BiometricsEvent.Success
import com.shashrwat.vault.features.login.LoginBiometricsState.NOT_ALLOWED
import com.shashrwat.vault.features.login.LoginCommand.EnterWithBiometrics
import com.shashrwat.vault.features.login.LoginCommand.EnterWithMasterPassword
import com.shashrwat.vault.features.login.LoginCommand.GetBiometricsEnterPossible
import com.shashrwat.vault.features.login.LoginCommand.GoToMainListScreen
import com.shashrwat.vault.features.login.LoginEvent.BiometricsEnterNotAllowed
import com.shashrwat.vault.features.login.LoginEvent.BiometricsEnterNotEnabled
import com.shashrwat.vault.features.login.LoginEvent.BiometricsEnterPossible
import com.shashrwat.vault.features.login.LoginEvent.ShowFailureCheckingBiometrics
import com.shashrwat.vault.features.login.LoginEvent.ShowFailureCheckingPassword
import com.shashrwat.vault.features.login.LoginEvent.ShowLoginSuccess
import com.shashrwat.vault.features.login.LoginNews.ShowBiometricsPrompt
import com.shashrwat.vault.features.login.LoginNews.ShowKeyboard
import com.shashrwat.vault.features.login.LoginUiEvent.OnBiometricsEvent
import com.shashrwat.vault.features.login.LoginUiEvent.OnBiometricsIconClicked
import com.shashrwat.vault.features.login.LoginUiEvent.OnEnterWithPassword
import com.shashrwat.vault.features.login.LoginUiEvent.OnInit
import com.shashrwat.vault.features.login.LoginUiEvent.OnTypingText

class LoginReducer : DslReducer<LoginState, LoginEvent, LoginCommand, LoginNews>() {
  
  override fun dslReduce(event: LoginEvent) {
    when (event) {
      OnInit -> {
        commands(GetBiometricsEnterPossible)
      }
      is BiometricsEnterPossible -> {
        state { copy(biometricsEnabled = true, biometricsIv = event.iv) }
        news(ShowBiometricsPrompt(event.iv))
      }
      BiometricsEnterNotEnabled -> {
        state { copy(biometricsEnabled = false) }
        news(ShowKeyboard)
      }
      BiometricsEnterNotAllowed -> {
        state { copy(biometricsEnabled = true, biometricsState = NOT_ALLOWED) }
        news(ShowKeyboard)
      }
      OnBiometricsIconClicked -> {
        val iv = state.biometricsIv
        if (iv != null) {
          news(ShowBiometricsPrompt(iv))
        }
      }
      is OnBiometricsEvent -> {
        when (event.event) {
          is Success -> {
            state { copy(showLoading = true, biometricsState = LoginBiometricsState.OK) }
            commands(EnterWithBiometrics(event.event.cryptography))
          }
          is Error -> {
            when (event.event.error) {
              LOCKOUT -> {
                state { copy(biometricsState = LoginBiometricsState.LOCKOUT) }
              }
              LOCKOUT_PERMANENT -> {
                state { copy(biometricsState = LoginBiometricsState.LOCKOUT_PERMANENT) }
              }
              OTHER -> {
                state { copy(biometricsState = LoginBiometricsState.OTHER_ERROR) }
              }
              CANCELLED -> {
                news(ShowKeyboard)
              }
            }
          }
        }
      }
      OnTypingText -> {
        state { copy(showPasswordIsIncorrect = false) }
      }
      is OnEnterWithPassword -> {
        state { copy(showLoading = true, showPasswordIsIncorrect = false) }
        commands(EnterWithMasterPassword(event.password))
      }
      ShowLoginSuccess -> {
        state { copy(showLoading = false) }
        commands(GoToMainListScreen)
      }
      ShowFailureCheckingPassword -> {
        state { copy(showLoading = false, showPasswordIsIncorrect = true) }
      }
      ShowFailureCheckingBiometrics -> {
        state { copy(showLoading = false, biometricsState = LoginBiometricsState.OTHER_ERROR) }
      }
    }
  }
}
