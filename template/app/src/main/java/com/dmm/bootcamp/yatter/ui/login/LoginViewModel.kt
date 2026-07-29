package com.dmm.bootcamp.yatter.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter.domain.model.LoginPassword
import com.dmm.bootcamp.yatter.domain.model.Username
import com.dmm.bootcamp.yatter.usecase.login.LoginUseCase
import com.dmm.bootcamp.yatter.usecase.login.LoginUseCaseResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface LoginNavigationEvent {
  data object LoggedIn : LoginNavigationEvent
  data object NavigatedToRegister : LoginNavigationEvent
}

class LoginViewModel (
  // POSTの画面なのでRepoじゃなくてUseCase
  private val loginUseCase: LoginUseCase,
): ViewModel() {
  private val _uiState: MutableStateFlow<LoginUiState> =
    MutableStateFlow(LoginUiState.empty())
  val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

  // 画面遷移の通知
  private val _navigationEvent = Channel<LoginNavigationEvent>(Channel.BUFFERED)
  // 外部公開
  val navigationEvent: Flow<LoginNavigationEvent> = _navigationEvent.receiveAsFlow()

  // ユザネ変更
  fun onChangedUsername(username: String) {
    val snapshotBindingModel = uiState.value.loginBindingModel
    _uiState.update {
      it.copy(
        validUsername = Username(username).validate(),
        loginBindingModel = snapshotBindingModel.copy( username = username )
      )
    }
  }
  // パスワード変更
  fun onChangedPassword(password: String) {
    val snapshotBindingModel = uiState.value.loginBindingModel
    _uiState.update {
      it.copy(
        validPassword = LoginPassword(password).validate(),
        loginBindingModel = snapshotBindingModel.copy( password = password )
      )
    }
  }
  // ログインボタンクリック
  fun onClickLogin() {
    viewModelScope.launch {
      // ローディングに
      _uiState.update { it.copy(isLoading = true) }
      // ログイン処理
      val snapBindingModel = uiState.value.loginBindingModel
      when (
        loginUseCase.execute(
            Username(snapBindingModel.username),
            LoginPassword(snapBindingModel.password)
          )
      ) {
        is LoginUseCaseResult.Success -> {
          _navigationEvent.send(LoginNavigationEvent.LoggedIn)
        }
        is LoginUseCaseResult.Failure -> {
          // TODO:エラー表示
        }
      }
      // ローディング解除
      _uiState.update { it.copy(isLoading = false) }
    }
  }
  // クリック通知
  fun onClickRegister() {
    viewModelScope.launch {
      _navigationEvent.send(LoginNavigationEvent.NavigatedToRegister)
    }
  }
}
