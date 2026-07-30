package com.dmm.bootcamp.yatter.ui.updateuser

import com.dmm.bootcamp.yatter.domain.model.User
import com.dmm.bootcamp.yatter.ui.updateuser.bindingmodel.UpdateUserBindingModel

data class UpdateUserUiState(
  val me: User?,
  val initialData: UpdateUserBindingModel?,
  val bindingModel: UpdateUserBindingModel,
  val isLoading: Boolean,
) {
  companion object {
    fun empty(): UpdateUserUiState = UpdateUserUiState(
      me = null,
      initialData = null,
      bindingModel = UpdateUserBindingModel(
        displayName = null,
        note = null,
        avatar = null,
      ),
      isLoading = true,
    )
  }

  val validAvatar: Boolean = true //TODO: 画像形式バリデーション
}
