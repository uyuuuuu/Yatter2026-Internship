package com.dmm.bootcamp.yatter.ui.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterPage(
  onRegistered: () -> Unit,
  onNavigatedToLogin: () -> Unit,
  registerViewModel: RegisterViewModel = koinViewModel(),
) {
  val uiState by registerViewModel.uiState.collectAsStateWithLifecycle()

  LaunchedEffect(registerViewModel) {
    registerViewModel.navigationEvent.collect { navigationEvent ->
      when (navigationEvent) {
        RegisterNavigationEvent.Registered -> onRegistered()
        RegisterNavigationEvent.NavigatedToLogin -> onNavigatedToLogin()
      }
    }
  }

  RegisterTemplate(
    userName = uiState.bindingModel.username,
    onChangedUserName = registerViewModel::onChangedUsername,
    password = uiState.bindingModel.password,
    onChangedPassword = registerViewModel::onChangedPassword,
    isEnableRegister = uiState.isEnableRegister,
    isLoading = uiState.isLoading,
    onClickRegister = registerViewModel::onClickRegister,
    onClickNavIcon = registerViewModel::onClickLogin,
  )
}
