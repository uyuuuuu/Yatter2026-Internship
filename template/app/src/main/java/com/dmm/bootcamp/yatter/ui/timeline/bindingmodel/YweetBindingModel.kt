package com.dmm.bootcamp.yatter.ui.timeline.bindingmodel

// YweetのBindingModel。Yweetから変換する

data class YweetBindingModel(
  val id: String,
  val displayName: String,
  val username: String,
  val avatar: String?,
  val content: String,
  val attachmentImageList: List<ImageBindingModel>
)
