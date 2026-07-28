package com.dmm.bootcamp.yatter.ui.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter.domain.repository.YweetRepository
import com.dmm.bootcamp.yatter.ui.timeline.bindingmodel.converter.YweetConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PublicTimelineViewModel(
  private val yweetRepository: YweetRepository,
) : ViewModel() {
  //  変更可能
  private val _uiState: MutableStateFlow<PublicTimelineUiState> =
    MutableStateFlow(PublicTimelineUiState.empty())
  //  外部公開
  val uiState: StateFlow<PublicTimelineUiState> = _uiState.asStateFlow()

  //  Yweetの一覧を取得するためのメソッド
  private suspend fun fetchPublicTimeline() {
    val yweetList = yweetRepository.findAllPublic() // 一覧取得
    _uiState.update {
      it.copy( // 一部のみを更新して他の値はそのまま
        yweetList = YweetConverter.convertToBindingModel(yweetList),
      )
    }
  }

  //  onResume=画面表示時 の中身実装
  fun onResume() {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true) }
      fetchPublicTimeline()
      _uiState.update { it.copy(isLoading = false) }
    }
  }

  //  onRefresh onResumeと同じ
  fun onRefresh() {
    viewModelScope.launch {
      _uiState.update { it.copy(isRefreshing = true) }
      fetchPublicTimeline()
      _uiState.update { it.copy(isRefreshing = false) }
    }
  }
}
