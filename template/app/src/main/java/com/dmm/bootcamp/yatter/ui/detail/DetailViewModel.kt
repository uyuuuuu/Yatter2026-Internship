package com.dmm.bootcamp.yatter.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter.domain.model.YweetId
import com.dmm.bootcamp.yatter.domain.repository.YweetRepository
import com.dmm.bootcamp.yatter.ui.detail.bindingmodel.converter.YweetConverter.convertToBindingModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface DetailNavigationEvent {
  data object NavigateToTimeLine : DetailNavigationEvent
}

class DetailViewModel(
  private val yweetRepository: YweetRepository,
) : ViewModel() {
  private val _uiState: MutableStateFlow<DetailUiState> =
    MutableStateFlow(DetailUiState.empty())
  val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

  private val _navigationEvent = Channel<DetailNavigationEvent>(Channel.BUFFERED)
  val navigationEvent: Flow<DetailNavigationEvent> = _navigationEvent.receiveAsFlow()


  fun onCreate(yweetId: String) {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true) }

      val yweet = yweetRepository.findById(YweetId(yweetId))

      if (yweet != null) {
        _uiState.update { it.copy(
          bindingModel = convertToBindingModel(yweet),
          isLoading = false
        )}
      }
    }
  }


  fun onClicktoTimeLine() {
    viewModelScope.launch {
      _navigationEvent.send(DetailNavigationEvent.NavigateToTimeLine)
    }
  }
}
