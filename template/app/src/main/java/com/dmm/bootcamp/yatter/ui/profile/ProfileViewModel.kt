package com.dmm.bootcamp.yatter.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter.domain.model.Username
import com.dmm.bootcamp.yatter.domain.repository.UserRepository
import com.dmm.bootcamp.yatter.domain.repository.YweetRepository
import com.dmm.bootcamp.yatter.domain.service.GetLoginUsernameService
import com.dmm.bootcamp.yatter.ui.timeline.bindingmodel.converter.YweetConverter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface ProfileNavigationEvent {
  data class NavigateToUpdateUser(val username: String): ProfileNavigationEvent
  data class NavigateToDetail(val yweetId: String): ProfileNavigationEvent
  data object Back: ProfileNavigationEvent
}

class ProfileViewModel(
  private val yweetRepository: YweetRepository,
  private val userRepository: UserRepository,
  private val usernameService: GetLoginUsernameService,
): ViewModel() {
  private val _uiState: MutableStateFlow<ProfileUiState> =
    MutableStateFlow(ProfileUiState.empty())
  //  外部公開
  val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

  private val _navigationEvent = Channel<ProfileNavigationEvent>(Channel.BUFFERED)
  val navigationEvent: Flow<ProfileNavigationEvent> = _navigationEvent.receiveAsFlow()

  //  このユーザーのYweetの一覧を取得するためのメソッド
  private suspend fun fetchUserTimeline(username: String) {
    val snapshotBindingModel = uiState.value.bindingModel
    val yweetList = yweetRepository.findAllPublic() // 一覧取得
    val myYweetList = yweetList.filter {
      it.user.username.value == username
    }
    _uiState.update {
      it.copy(
        bindingModel = snapshotBindingModel.copy(
          yweetList = YweetConverter.convertToBindingModel(myYweetList),
        )
      )
    }
  }

  fun onCreate(username: String) {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true) }
      val snapshotBindingModel = uiState.value.bindingModel
      val myUserName = usernameService.execute()
      val user = userRepository.findByUsername(Username(username), false)
      if (user != null) {
        _uiState.update { it.copy(
          bindingModel = snapshotBindingModel.copy(
            username = username,
            displayName = user.displayName,
            note = user.note,
            avatar = user.avatar?.toString(),
            ),
          myUserName = myUserName?.value.orEmpty(),
        )}
        fetchUserTimeline(username)
      }
      _uiState.update { it.copy(isLoading = false) }
    }
  }

  fun onClickUpdateUser() {
    val snapshotBindingModel = uiState.value.bindingModel
    viewModelScope.launch {
      _navigationEvent.send(ProfileNavigationEvent.NavigateToUpdateUser(snapshotBindingModel.username.toString()))
    }
  }
  fun onClickYweet(yweetId: String) {
    viewModelScope.launch {
      _navigationEvent.send(ProfileNavigationEvent.NavigateToDetail(yweetId))
    }
  }
  fun onClickNavIcon() {
    viewModelScope.launch {
      _navigationEvent.send(ProfileNavigationEvent.Back)
    }
  }
  fun onResume() {
    viewModelScope.launch {
      _uiState.update { it.copy(
        isLoading = true
      ) }
      fetchUserTimeline(uiState.value.bindingModel.username)
      _uiState.update { it.copy(
        isLoading = false
      ) }
    }
  }

  fun onRefresh() {
    viewModelScope.launch {
      _uiState.update { it.copy(isRefreshing = true) }
      fetchUserTimeline(uiState.value.bindingModel.username)
      _uiState.update { it.copy(isRefreshing = false) }
    }
  }
}
