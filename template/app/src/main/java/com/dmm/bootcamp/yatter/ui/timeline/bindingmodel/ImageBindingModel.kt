package com.dmm.bootcamp.yatter.ui.timeline.bindingmodel

// 画像のBindingModel。Imageから変換する
data class ImageBindingModel (
  val id: String,
  val type: String,
  val url: String,
  val description: String?
)
