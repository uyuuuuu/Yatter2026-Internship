package com.dmm.bootcamp.yatter.ui.timeline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun PublicTimelinePage(
  onNavigateToPost: () -> Unit,
  onNavigateToLogin: () -> Unit,
  // koinViewModelはDIからViewModelを呼び出す
  publicTimelineViewModel: PublicTimelineViewModel = koinViewModel(),
) {
  // collectAsStateWithLifecycle()->Stateという型にする
  val uiState by publicTimelineViewModel.uiState.collectAsStateWithLifecycle()

  // ライフサイクル
  LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
    publicTimelineViewModel.onResume()
  }

  // 投稿画面へ
  LaunchedEffect(publicTimelineViewModel) {
    publicTimelineViewModel.navigationEvent.collect { ev ->
      when (ev) {
        is PublicTimelineNavigationEvent.NavigateToPost -> {
          onNavigateToPost()
        }
        is PublicTimelineNavigationEvent.NavigateToLogin -> {
          onNavigateToLogin()
        }
      }
    }
  }

  PublicTimelineTemplate(
    yweetList = uiState.yweetList,
    onClickPost = publicTimelineViewModel::onClickPost,
    isLoading = uiState.isLoading,
    isRefreshing = uiState.isRefreshing,
    onRefresh = publicTimelineViewModel::onRefresh, //::は後で実行させる
    onLogout =publicTimelineViewModel::onClickLogout,
  )
}
