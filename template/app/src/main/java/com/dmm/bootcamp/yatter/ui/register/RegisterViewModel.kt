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
  data class Registered(val username: String) : RegisterNavigationEvent
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
    _uiState.update {
      it.copy(
        validUsername = Username(username).validate(),
        errorMessage = null,
        bindingModel = it.bindingModel.copy( username = username )
      )
    }
  }
  // パスワード変更
  fun onChangedPassword(password: String) {
    _uiState.update {
      it.copy(
        validPassword = RegisterPassword(password).validate(),
        errorMessage = null,
        bindingModel = it.bindingModel.copy( password = password )
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
      val result = registerUserUseCase.execute(
        snapBindingModel.username,
        snapBindingModel.password
      )
      when (
        result
      ) {
        is RegisterUserUseCaseResult.Success -> {
          _navigationEvent.send(RegisterNavigationEvent.Registered(snapBindingModel.username))
        }
        is RegisterUserUseCaseResult.Failure -> {
          val message =
            when (result) {
            is RegisterUserUseCaseResult.Failure.EmptyUsername -> "ユーザー名を入力してください"
            is RegisterUserUseCaseResult.Failure.EmptyPassword -> "パスワードを入力してください"
            is RegisterUserUseCaseResult.Failure.InvalidPassword -> "パスワードは8文字以上で、大文字・小文字・記号をそれぞれ1文字以上含めてください"
            is RegisterUserUseCaseResult.Failure.CreateUserError -> "登録に失敗しました。ユーザー名が既に使われている可能性があります"
            is RegisterUserUseCaseResult.Failure.LoginError -> "登録はできましたが、ログインに失敗しました"
          }
          _uiState.update { it.copy(errorMessage = message) }
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
