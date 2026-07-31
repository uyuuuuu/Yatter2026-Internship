package com.dmm.bootcamp.yatter.ui.post

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter.domain.service.GetLoginUserService
import com.dmm.bootcamp.yatter.usecase.post.PostYweetUseCase
import com.dmm.bootcamp.yatter.usecase.post.PostYweetUseCaseResult
import com.dmm.bootcamp.yatter.util.ImageConverter.uriToFile
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface PostNavigationEvent {
  data object Posted : PostNavigationEvent
  data object Back : PostNavigationEvent
}

class PostViewModel(
  private val postYweetUseCase: PostYweetUseCase,
  private val getLoginUserService: GetLoginUserService
): ViewModel() {
  private val _uiState: MutableStateFlow<PostUiState> = MutableStateFlow(PostUiState.empty())
  val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

  private val _navigationEvent = Channel<PostNavigationEvent>(Channel.BUFFERED)
  val navigationEvent: Flow<PostNavigationEvent> = _navigationEvent.receiveAsFlow()

  fun onCreate() {
    // ユーザー情報取得のコルーチン起動
    viewModelScope.launch {
      _uiState.update { it.copy( isLoading = true ) }
      val me = getLoginUserService.execute()
      val snapshotBindingModel = uiState.value.bindingModel
      _uiState.update { it.copy(
        bindingModel = snapshotBindingModel.copy(
          avatarUrl = me?.avatar.toString()
        ),
        isLoading = false,
      )}
    }
  }

  fun onChangedYweetText(yweetText: String) {
    _uiState.update { it.copy(
      bindingModel = uiState.value.bindingModel.copy(
        yweetText = yweetText
      )
    )}
  }

  fun onSelectImage(uri: Uri) {
    val currentUris = uiState.value.bindingModel.attachmentImageUris
    if (currentUris.size >= MAX_ATTACHMENT_COUNT) {
      return
    }
    _uiState.update {
      it.copy(
        bindingModel = it.bindingModel.copy(
          attachmentImageUris = currentUris + uri,
        ),
      )
    }
  }

  fun onClickRemoveImage(uri: Uri) {
    _uiState.update {
      it.copy(
        bindingModel = it.bindingModel.copy(
          attachmentImageUris = it.bindingModel.attachmentImageUris.filterNot { imageUri ->
            imageUri == uri
          },
        ),
      )
    }
  }

  // 投稿ボタン 画像があるのでContext
  fun onClickPost(context: Context) {
    viewModelScope.launch {
      _uiState.update { it.copy( isLoading = true ) }
      val attachmentList = uiState.value.bindingModel.attachmentImageUris.mapNotNull { uri ->
        uriToFile(context, uri)
      }
      val result = postYweetUseCase.execute(
        content = uiState.value.bindingModel.yweetText,
        attachmentList = attachmentList
      )
      when(result) {
        is PostYweetUseCaseResult.Success -> {
          _navigationEvent.send(PostNavigationEvent.Posted)
        }
        is PostYweetUseCaseResult.Failure -> {
          // TODO: エラー表示
        }
      }
      _uiState.update { it.copy( isLoading = false ) }
    }
  }

  // 戻るボタン
  fun onClickNavIcon() {
    viewModelScope.launch {
      _navigationEvent.send(PostNavigationEvent.Back)
    }
  }

  companion object {
    const val MAX_ATTACHMENT_COUNT = 4
  }
}
