package com.shashrwat.vault.features.common.extensions

import navigation.BaseFragmentScreen

fun BaseFragmentScreen.setStatusBarColor(color: Int) {
  requireActivity().window.statusBarColor = color
}
