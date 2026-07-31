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
fun UpdateUserPage(
  username: String,
  onBack: () -> Unit,
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
    updateUserViewModel.onCreateWithDefault(Username(username))
  }

  LaunchedEffect(updateUserViewModel) {
    updateUserViewModel.navigationEvent.collect { ev ->
      when (ev) {
        is UpdateUserNavigationEvent.NavigatedToBack -> {
          onBack()
        }
        else -> {}
      }
    }
  }

  UpdateUserTemplate(
    displayName = uiState.bindingModel.displayName,
    onChangedDisplayName = {displayname -> updateUserViewModel.onChangedDisplayName(displayname)},
    note = uiState.bindingModel.note,
    onChangedNote = {note -> updateUserViewModel.onChangedNote(note)},
    avatar = uiState.bindingModel.avatar,
    onClickSelectImage = {imagePickerLauncher.launch("image/*")},
    onChangedAvatar = {avatar -> updateUserViewModel.onChangedAvatar(avatar)},
    isLoading = false,
    onClickUpdate = {updateUserViewModel.onClickUpdate(context)},
    onClickNavIcon = updateUserViewModel::onClickNavIcon,
  )
}
