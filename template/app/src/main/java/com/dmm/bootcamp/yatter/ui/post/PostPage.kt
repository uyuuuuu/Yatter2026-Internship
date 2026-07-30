package com.dmm.bootcamp.yatter.ui.post

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun PostPage(
  onPosted: () -> Unit,
  onBack: () -> Unit,
  postViewModel: PostViewModel = koinViewModel(),
) {
  val uiState by postViewModel.uiState.collectAsStateWithLifecycle()
  val context = LocalContext.current

  LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
    postViewModel.onCreate()
  }

  LaunchedEffect(postViewModel) { //非同期処理
    // イベント受信
    postViewModel.navigationEvent.collect { navigationEvent ->
      when (navigationEvent) {
        is PostNavigationEvent.Posted -> onPosted()
        is PostNavigationEvent.Back -> onBack()
      }
    }
  }

  PostTemplate(
    postBindingModel = uiState.bindingModel,
    isLoading = uiState.isLoading,
    canPost = uiState.canPost,
    onYweetTextChanged = postViewModel::onChangedYweetText,
    onClickPost = { postViewModel.onClickPost(context) },
    onClickNavIcon = postViewModel::onClickNavIcon,
  )
}
