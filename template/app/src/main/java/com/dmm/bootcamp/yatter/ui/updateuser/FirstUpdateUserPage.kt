package com.dmm.bootcamp.yatter.ui.updateuser

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter.domain.model.Username
import org.koin.androidx.compose.koinViewModel

@Composable
fun FirstUpdateUserPage(
  username: String,
  onNavigatedToTimeLine: () -> Unit,
  updateUserViewModel: UpdateUserViewModel = koinViewModel(),
) {
  val uiState by updateUserViewModel.uiState.collectAsStateWithLifecycle()
  val context = LocalContext.current

  val imagePickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent(),
  ) { uri ->
    uri ?: return@rememberLauncherForActivityResult
    updateUserViewModel.onSelectImage(uri)
  }

  LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
    updateUserViewModel.onCreate(Username(username))
  }

  LaunchedEffect(updateUserViewModel) {
    updateUserViewModel.navigationEvent.collect { ev ->
      when (ev) {
        is UpdateUserNavigationEvent.NavigatedToTimeLine -> {
          onNavigatedToTimeLine()
        }
        else -> {}
      }
    }
  }

  FirstUpdateUserTemplate(
    displayName = uiState.bindingModel.displayName,
    onChangedDisplayName = {displayName -> updateUserViewModel.onChangedDisplayName(displayName)},
    note = uiState.bindingModel.note,
    onChangedNote = {note -> updateUserViewModel.onChangedNote(note)},
    avatar = uiState.bindingModel.avatar,
    onClickSelectImage = {imagePickerLauncher.launch("image/*")},
    isLoading = false,
    onClickRegister = {updateUserViewModel.onClickRegister(context)},
    onClickSkip = updateUserViewModel::onClickSkip,
  )
}
