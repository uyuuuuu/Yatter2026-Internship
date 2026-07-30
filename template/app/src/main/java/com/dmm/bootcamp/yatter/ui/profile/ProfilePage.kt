package com.dmm.bootcamp.yatter.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfilePage(
  username: String,
  onNavToUpdateUser: (String) -> Unit,
  onNavToDetail: (String) -> Unit,
  onBack: () -> Unit,
  profileViewModel: ProfileViewModel = koinViewModel(),
) {
  val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()

  LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
    profileViewModel.onCreate(username)
  }

  LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
    profileViewModel.onResume()
  }

  LaunchedEffect(profileViewModel) { //非同期処理
    // イベント受信
    profileViewModel.navigationEvent.collect { navigationEvent ->
      when (navigationEvent) {
        is ProfileNavigationEvent.NavigateToUpdateUser -> onNavToUpdateUser(navigationEvent.username)
        is ProfileNavigationEvent.NavigateToDetail -> onNavToDetail(navigationEvent.yweetId)
        is ProfileNavigationEvent.Back -> onBack()
      }
    }
  }

  ProfileTemplate(
    bindingModel = uiState.bindingModel,
    isMyUser = uiState.isMyUser,
    onClickUpdateUser = { profileViewModel.onClickUpdateUser() },
    onClickYweet = {id -> profileViewModel.onClickYweet(id) },
    onClickNavIcon = profileViewModel::onClickNavIcon,
    isLoading = uiState.isLoading,
    isRefreshing = uiState.isRefreshing,
    onRefresh = profileViewModel::onRefresh,
  )
}
