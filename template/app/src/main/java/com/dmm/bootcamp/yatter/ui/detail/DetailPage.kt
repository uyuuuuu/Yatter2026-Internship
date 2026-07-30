package com.dmm.bootcamp.yatter.ui.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailPage(
  yweetId: String,
  onBack: () -> Unit,
  detailViewModel: DetailViewModel = koinViewModel(),
) {
  val uiState by detailViewModel.uiState.collectAsStateWithLifecycle()

  LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
    detailViewModel.onCreate(yweetId)
  }

  // タイムライン画面へ
  LaunchedEffect(detailViewModel) {
    detailViewModel.navigationEvent.collect { ev ->
      when (ev) {
        is DetailNavigationEvent.NavigateToTimeLine -> {
          onBack()
        }
      }
    }
  }

  DetailTemplate(
    yweet = uiState.bindingModel,
    onClickNavIcon = detailViewModel::onClicktoTimeLine,
    isLoading = uiState.isLoading,
  )
}
