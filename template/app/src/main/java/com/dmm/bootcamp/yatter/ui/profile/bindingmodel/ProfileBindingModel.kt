package com.dmm.bootcamp.yatter.ui.profile.bindingmodel

import com.dmm.bootcamp.yatter.ui.timeline.bindingmodel.YweetBindingModel

data class ProfileBindingModel(
  val username: String,
  val displayName: String?,
  val note: String?,
  val avatar: Any?,
  val yweetList: List<YweetBindingModel>,
)
