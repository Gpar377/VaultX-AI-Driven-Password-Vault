package com.shashrwat.vault.features.login

import android.content.Context
import android.view.Gravity.CENTER
import android.view.Gravity.CENTER_HORIZONTAL
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.shashrwat.vault.BuildConfig
import com.shashrwat.vault.R
import com.shashrwat.vault.core.mvi.ext.subscribe
import com.shashrwat.vault.core.mvi.ext.viewModelStore
import com.shashrwat.vault.core.views.EditTextPassword
import com.shashrwat.vault.core.views.FixedHeightTextView
import com.shashrwat.vault.features.common.Durations
import com.shashrwat.vault.features.common.biometrics.BiometricsDialog
import com.shashrwat.vault.features.common.di.CoreComponentHolder.coreComponent
import com.shashrwat.vault.features.common.dialogs.LoadingDialog
import com.shashrwat.vault.features.common.dialogs.loadingDialog
import com.shashrwat.vault.features.common.extensions.setStatusBarColor
import com.shashrwat.vault.features.login.LoginBiometricsState.LOCKOUT
import com.shashrwat.vault.features.login.LoginBiometricsState.LOCKOUT_PERMANENT
import com.shashrwat.vault.features.login.LoginBiometricsState.NOT_ALLOWED
import com.shashrwat.vault.features.login.LoginBiometricsState.OK
import com.shashrwat.vault.features.login.LoginBiometricsState.OTHER_ERROR
import com.shashrwat.vault.features.login.LoginNews.ShowBiometricsPrompt
import com.shashrwat.vault.features.login.LoginNews.ShowKeyboard
import com.shashrwat.vault.features.login.LoginUiEvent.OnBiometricsEvent
import com.shashrwat.vault.features.login.LoginUiEvent.OnBiometricsIconClicked
import com.shashrwat.vault.features.login.LoginUiEvent.OnEnterWithPassword
import com.shashrwat.vault.features.login.LoginUiEvent.OnInit
import com.shashrwat.vault.features.login.LoginUiEvent.OnTypingText
import com.shashrwat.vault.viewbuilding.Colors
import com.shashrwat.vault.viewbuilding.Dimens.CircleButtonSize
import com.shashrwat.vault.viewbuilding.Dimens.ImageLogoSize
import com.shashrwat.vault.viewbuilding.Dimens.MarginExtraLarge
import com.shashrwat.vault.viewbuilding.Dimens.MarginLarge
import com.shashrwat.vault.viewbuilding.Dimens.MarginMedium
import com.shashrwat.vault.viewbuilding.Dimens.MarginNormal
import com.shashrwat.vault.viewbuilding.Dimens.MarginSmall
import com.shashrwat.vault.viewbuilding.Dimens.MarginTiny
import com.shashrwat.vault.viewbuilding.Styles.BaseTextView
import com.shashrwat.vault.viewbuilding.Styles.BoldTextView
import com.shashrwat.vault.viewbuilding.Styles.Button
import com.shashrwat.vault.viewbuilding.Styles.SecondaryTextView
import com.shashrwat.vault.viewbuilding.TextSizes
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import navigation.BaseFragmentScreen
import viewdsl.Size.Companion.MatchParent
import viewdsl.Size.Companion.WrapContent
import viewdsl.Size.IntSize
import viewdsl.clearText
import viewdsl.constraints
import viewdsl.gravity
import viewdsl.hideKeyboard
import viewdsl.id
import viewdsl.image
import viewdsl.imageTint
import viewdsl.isVisible
import viewdsl.margin
import viewdsl.marginHorizontal
import viewdsl.margins
import viewdsl.onClick
import viewdsl.rotate
import viewdsl.text
import viewdsl.textColor
import viewdsl.textSize
import viewdsl.withViewBuilder

class LoginScreen : BaseFragmentScreen() {
  
  override fun buildLayout(context: Context) = context.withViewBuilder {
    RootConstraintLayout {
      VerticalLayout(WrapContent, WrapContent) {
        id(LogoLayout)
        constraints {
          topToTopOf(parent)
          startToStartOf(parent)
          endToEndOf(parent)
          bottomToTopOf(LayoutContent)
        }
        gravity(CENTER)
        ImageView(ImageLogoSize, ImageLogoSize) {
          image(R.mipmap.ic_launcher)
          margin(MarginNormal)
          onClick { rotate() }
        }
        TextView(WrapContent, WrapContent, style = BoldTextView) {
          textSize(TextSizes.H0)
          text(R.string.app_name)
        }
      }
      VerticalLayout(MatchParent, WrapContent) {
        id(LayoutContent)
        margins(top = MarginExtraLarge * 2)
        child<EditTextPassword>(MatchParent, WrapContent) {
          id(EditTextPassword)
          marginHorizontal(MarginNormal)
          if (BuildConfig.DEBUG) {
            setRawText(BuildConfig.STUB_PASSWORD)
            editText.setSelection(BuildConfig.STUB_PASSWORD.length)
          }
          setHint(R.string.text_enter_password)
          onTextChanged { store.tryDispatch(OnTypingText) }
          onSubmit { text -> store.tryDispatch(OnEnterWithPassword(text)) }
        }
        child<FixedHeightTextView>(MatchParent, WrapContent, style = BaseTextView) {
          id(TextError)
          margins(start = MarginNormal + MarginTiny, top = MarginSmall)
          textColor(Colors.TextError)
        }
      }
      VerticalLayout(WrapContent, WrapContent) {
        id(LayoutBiometrics)
        gravity(CENTER)
        margins(bottom = MarginLarge)
        onClick { store.tryDispatch(OnBiometricsIconClicked) }
        constraints {
          setVerticalBias(0.8f)
          startToStartOf(parent)
          topToBottomOf(LayoutContent)
          endToEndOf(parent)
          bottomToTopOf(ButtonContinue)
        }
        ImageView(IntSize(CircleButtonSize), IntSize(CircleButtonSize)) {
          id(ImageBiometrics)
          image(R.drawable.ic_fingerprint)
          margins(bottom = MarginMedium)
        }
        child<FixedHeightTextView>(WrapContent, WrapContent, style = SecondaryTextView) {
          minTextHeightTextRes = R.string.text_biometrics_error_lockout
          id(TextBiometrics)
          gravity(CENTER_HORIZONTAL)
          marginHorizontal(MarginNormal)
        }
      }
      TextView(MatchParent, WrapContent, style = Button()) {
        id(ButtonContinue)
        text(R.string.text_continue)
        margins(start = MarginNormal, end = MarginNormal, bottom = MarginNormal)
        constraints {
          startToStartOf(parent)
          endToEndOf(parent)
          bottomToBottomOf(parent)
        }
        onClick {
          val password = viewAs<EditTextPassword>(EditTextPassword).getPassword()
          store.tryDispatch(OnEnterWithPassword(password))
        }
      }
      LoadingDialog()
    }
  }
  
  private val store by viewModelStore { LoginStore(coreComponent) }
  
  private val biometricsDialog by lazy {
    BiometricsDialog.create(this, R.string.text_enter_with_biometrics)
  }
  
  override fun onInit() {
    store.subscribe(this, ::render, ::handleNews)
    store.tryDispatch(OnInit)
    initBiometricsDialog()
  }
  
  @OptIn(FlowPreview::class)
  private fun initBiometricsDialog() {
    biometricsDialog.events
        .onEach { event -> store.tryDispatch(OnBiometricsEvent(event)) }
        .launchIn(lifecycleScope)
    biometricsDialog.openedStatus
        // In case dialog opens and closes very fast due to existing error
        .debounce(100L)
        .onEach { opened -> setStatusBarColor(if (opened) Colors.Black else Colors.Transparent) }
        .launchIn(lifecycleScope)
  }
  
  private fun render(state: LoginState) {
    if (state.showPasswordIsIncorrect) {
      textView(TextError).text(R.string.text_password_is_incorrect)
    } else {
      textView(TextError).clearText()
    }
    view(LayoutBiometrics).isVisible = state.biometricsEnabled
    view(LayoutContent).constraints {
      centeredWithin(parent)
      setVerticalBias(if (state.biometricsEnabled) 0.43f else 0.5f)
    }
    view(LayoutBiometrics).isClickable = state.biometricsState == OK
    val imageColor = if (state.biometricsState == OK) Colors.TextSecondary else Colors.TextDisabled
    imageView(ImageBiometrics).imageTint(imageColor)
    val textResToColor = when (state.biometricsState) {
      OK -> R.string.text_biometrics_tap_here to Colors.TextSecondary
      NOT_ALLOWED -> R.string.text_biometrics_error_not_allowed to Colors.TextSecondary
      LOCKOUT -> R.string.text_biometrics_error_lockout to Colors.TextError
      LOCKOUT_PERMANENT -> R.string.text_biometrics_error_lockout_permanent to Colors.TextError
      OTHER_ERROR -> R.string.text_biometrics_error_other to Colors.TextError
    }
    textView(TextBiometrics).text(textResToColor.first)
    textView(TextBiometrics).textColor(textResToColor.second)
    if (state.showLoading) {
      loadingDialog.show()
    } else {
      loadingDialog.hide()
    }
  }
  
  private fun handleNews(news: LoginNews) {
    when (news) {
      ShowKeyboard -> {
        requireView().postDelayed({
          viewAsNullable<EditTextPassword>(EditTextPassword)?.showKeyboard()
        }, Durations.DelayOpenKeyboard)
      }
      is ShowBiometricsPrompt -> {
        val cipher = coreComponent.biometricsCipherProvider.getCipherForDecryption(news.iv)
        biometricsDialog.launch(cipher)
      }
    }
  }
  
  override fun onDestroyView() {
    super.onDestroyView()
    requireContext().hideKeyboard()
  }
  
  companion object {
    
    val LogoLayout = View.generateViewId()
    val LayoutContent = View.generateViewId()
    val TextError = View.generateViewId()
    val EditTextPassword = View.generateViewId()
    val LayoutBiometrics = View.generateViewId()
    val ImageBiometrics = View.generateViewId()
    val TextBiometrics = View.generateViewId()
    val ButtonContinue = View.generateViewId()
  }
}
