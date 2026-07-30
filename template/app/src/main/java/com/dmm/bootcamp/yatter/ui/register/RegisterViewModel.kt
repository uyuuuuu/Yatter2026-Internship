package com.dmm.bootcamp.yatter.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter.domain.model.RegisterPassword
import com.dmm.bootcamp.yatter.domain.model.Username
import com.dmm.bootcamp.yatter.usecase.register.RegisterUserUseCase
import com.dmm.bootcamp.yatter.usecase.register.RegisterUserUseCaseResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface RegisterNavigationEvent {
  data object Registered : RegisterNavigationEvent
  data object NavigatedToLogin : RegisterNavigationEvent
}

class RegisterViewModel (
  private val registerUserUseCase: RegisterUserUseCase,
): ViewModel() {
  private val _uiState: MutableStateFlow<RegisterUiState> =
    MutableStateFlow(RegisterUiState.empty())
  val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

  private val _navigationEvent = Channel<RegisterNavigationEvent>(Channel.BUFFERED)
  val navigationEvent: Flow<RegisterNavigationEvent> = _navigationEvent.receiveAsFlow()

  // ユザネ変更
  fun onChangedUsername(username: String) {
    val snapshotBindingModel = uiState.value.bindingModel
    _uiState.update {
      it.copy(
        validUsername = Username(username).validate(),
        bindingModel = snapshotBindingModel.copy( username = username )
      )
    }
  }
  // パスワード変更
  fun onChangedPassword(password: String) {
    val snapshotBindingModel = uiState.value.bindingModel
    _uiState.update {
      it.copy(
        validPassword = RegisterPassword(password).validate(),
        bindingModel = snapshotBindingModel.copy( password = password )
      )
    }
  }
  // ログインボタンクリック
  fun onClickRegister() {
    viewModelScope.launch {
      // ローディングに
      _uiState.update { it.copy(isLoading = true) }
      // ログイン処理
      val snapBindingModel = uiState.value.bindingModel
      when (
        registerUserUseCase.execute(
          snapBindingModel.username,
          snapBindingModel.password
        )
      ) {
        is RegisterUserUseCaseResult.Success -> {
          _navigationEvent.send(RegisterNavigationEvent.Registered)
        }
        is RegisterUserUseCaseResult.Failure -> {
          // TODO:エラー表示
        }
      }
      // ローディング解除
      _uiState.update { it.copy(isLoading = false) }
    }
  }
  // ログインへ戻る
  fun onClickLogin() {
    viewModelScope.launch {
      _navigationEvent.send(RegisterNavigationEvent.NavigatedToLogin)
    }
  }
}
