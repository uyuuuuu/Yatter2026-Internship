package com.dmm.bootcamp.yatter.ui.updateuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter.domain.model.Username
import com.dmm.bootcamp.yatter.domain.repository.UserRepository
import com.dmm.bootcamp.yatter.ui.updateuser.bindingmodel.UpdateUserBindingModel
import com.dmm.bootcamp.yatter.usecase.update.UpdateUserUseCase
import com.dmm.bootcamp.yatter.usecase.update.UpdateUserUseCaseResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

sealed interface UpdateUserNavigationEvent {
  data object NavigatedToTimeLine : UpdateUserNavigationEvent
  data object NavigatedToBack : UpdateUserNavigationEvent
}

class UpdateUserViewModel(
  private val userRepository: UserRepository,
  private val updateUserUseCase: UpdateUserUseCase,
): ViewModel() {
  private val _uiState:MutableStateFlow<UpdateUserUiState> = MutableStateFlow(UpdateUserUiState.empty())
  val uiState: StateFlow<UpdateUserUiState> = _uiState.asStateFlow()

  private val _navigationEvent = Channel<UpdateUserNavigationEvent>(Channel.BUFFERED)
  val navigationEvent: Flow<UpdateUserNavigationEvent> = _navigationEvent.receiveAsFlow()

  fun onCreate(username: Username) {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true) }

      val me = userRepository.findByUsername(username, false)

      if (me != null) {
        _uiState.update { it.copy(
          me = me,
          isLoading = false
        )}
      }
    }
  }

  fun onCreateWithDefault(username: Username) {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true) }

      val me = userRepository.findByUsername(username, false)

      if (me != null) {
        _uiState.update { it.copy(
          me = me,
          bindingModel = UpdateUserBindingModel(
            displayName = me.displayName,
            note = me.note,
            avatar = me.avatar,
          ),
          isLoading = false
        )}
      }
    }
  }
  fun onChangedDisplayName(displayName: String) {
    val snapshotBindingModel = uiState.value.bindingModel
    _uiState.update {
      it.copy(
        bindingModel = snapshotBindingModel.copy( displayName = displayName )
      )
    }
  }
  fun onChangedNote(note: String) {
    val snapshotBindingModel = uiState.value.bindingModel
    _uiState.update {
      it.copy(
        bindingModel = snapshotBindingModel.copy( note = note )
      )
    }
  }
  fun onChangedAvatar(avatar: File) {

  }
  fun onClickRegister() {
    viewModelScope.launch {
      // ローディングに
      _uiState.update { it.copy(isLoading = true) }
      // ログイン処理
      val snapBindingModel = uiState.value.bindingModel
      val me = uiState.value.me
      if(me!=null) {
        val avatarFile = snapBindingModel.avatar as? File
        when (
          updateUserUseCase.execute(
            me = me,
            displayName = snapBindingModel.displayName,
            note = snapBindingModel.note,
            avatar = avatarFile
          )
        ) {
          is UpdateUserUseCaseResult.Success -> {
            _navigationEvent.send(UpdateUserNavigationEvent.NavigatedToTimeLine)
          }
          is UpdateUserUseCaseResult.Failure -> {
            // TODO:エラー表示
          }
        }
      }
      // ローディング解除
      _uiState.update { it.copy(isLoading = false) }
    }
  }

  fun onClickUpdate() {
    viewModelScope.launch {
      // ローディングに
      _uiState.update { it.copy(isLoading = true) }
      // ログイン処理
      val snapBindingModel = uiState.value.bindingModel
      val me = uiState.value.me
      if(me!=null) {
        val avatarFile = snapBindingModel.avatar as? File
        when (
          updateUserUseCase.execute(
            me = me,
            displayName = snapBindingModel.displayName,
            note = snapBindingModel.note,
            avatar = avatarFile
          )
        ) {
          is UpdateUserUseCaseResult.Success -> {
            _navigationEvent.send(UpdateUserNavigationEvent.NavigatedToBack)
          }
          is UpdateUserUseCaseResult.Failure -> {
            // TODO:エラー表示
          }
        }
      }
      // ローディング解除
      _uiState.update { it.copy(isLoading = false) }
    }
  }

  fun onClickSkip() {
    viewModelScope.launch {
      _navigationEvent.send(UpdateUserNavigationEvent.NavigatedToTimeLine)
    }
  }
  fun onClickNavIcon() {
    viewModelScope.launch {
      _navigationEvent.send(UpdateUserNavigationEvent.NavigatedToBack)
    }
  }
}
