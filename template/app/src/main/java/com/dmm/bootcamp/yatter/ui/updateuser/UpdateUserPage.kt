package com.dmm.bootcamp.yatter.ui.updateuser

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter.domain.model.Username
import org.koin.androidx.compose.koinViewModel

@Composable
fun UpdateUserPage(
  username: String,
  onNavigatedToTimeLine: () -> Unit,
  updateUserViewModel: UpdateUserViewModel = koinViewModel(),
) {
  val uiState by updateUserViewModel.uiState.collectAsStateWithLifecycle()

  LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
    updateUserViewModel.onCreate(Username(username))
  }

  LaunchedEffect(updateUserViewModel) {
    updateUserViewModel.navigationEvent.collect { ev ->
      when (ev) {
        is UpdateUserNavigationEvent.NavigatedToTimeLine -> {
          onNavigatedToTimeLine()
        }
      }
    }
  }

  UpdateUserTemplate(
    displayName = uiState.bindingModel.displayName,
    onChangedDisplayName = {displayname -> updateUserViewModel.onChangedDisplayName(displayname)},
    note = uiState.bindingModel.note,
    onChangedNote = {note -> updateUserViewModel.onChangedNote(note)},
    avatar = uiState.bindingModel.avatar,
    onChangedAvatar = {avatar -> updateUserViewModel.onChangedAvatar(avatar)},
    isLoading = false,
    onClickUpdate = updateUserViewModel::onClickUpdate,
    onClickSkip = updateUserViewModel::onClickSkip,
  )
}
