package com.dmm.bootcamp.yatter.ui.profile

import com.dmm.bootcamp.yatter.ui.profile.bindingmodel.ProfileBindingModel

data class ProfileUiState(
  val bindingModel: ProfileBindingModel,
  val myUserName: String,
  val isLoading: Boolean,
  val isRefreshing: Boolean,
) {
  val isMyUser = myUserName.isNotEmpty() && bindingModel.username == myUserName
  companion object {
    fun empty(): ProfileUiState = ProfileUiState(
      bindingModel = ProfileBindingModel(
        username = "",
        displayName = null,
        note = null,
        avatar = null,
        yweetList = emptyList()
      ),
      myUserName = "",
      isLoading = true,
      isRefreshing = false,
    )
  }
}
