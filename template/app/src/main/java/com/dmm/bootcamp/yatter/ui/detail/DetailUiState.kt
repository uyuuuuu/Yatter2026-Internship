package com.dmm.bootcamp.yatter.ui.detail

import com.dmm.bootcamp.yatter.ui.detail.bindingmodel.DetailBindingModel

data class DetailUiState (
  val bindingModel: DetailBindingModel,
  val isLoading: Boolean,
  ) {
    companion object {
      fun empty(): DetailUiState = DetailUiState(
        bindingModel = DetailBindingModel(
          id = "",
          displayName = "",
          username = "",
          avatar = null,
          content = "",
          attachmentImageList = emptyList()
        ),
        isLoading = false,
      )
    }
  }
