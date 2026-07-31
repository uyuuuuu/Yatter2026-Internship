package com.dmm.bootcamp.yatter.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.res.ResourcesCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dmm.bootcamp.yatter.R

@Composable
fun YatterAvatarIcon(
  avatar: Any?,
  modifier: Modifier = Modifier,
  contentDescription: String,
  onClick: (() -> Unit)? = null,
  ) {
  val context = LocalContext.current
  // プレイスホルダー画像の生成
  val placeholder = ResourcesCompat.getDrawable(
    context.resources,
    R.drawable.avatar_placeholder,
    null,
  )
  AsyncImage(
    modifier = modifier
      .clip(CircleShape)
      .then(
        if (onClick != null) {
          Modifier.clickable(onClick = onClick)
          } else { Modifier }
      ),
    // ImageRequestを作成して、
    // 画像取得できていない状態のプレイスホルダー設定
    model = ImageRequest.Builder(context)
      .data(avatar)
      .placeholder(placeholder)
      .error(placeholder)
      .fallback(placeholder)
      // モックサーバーから画像取得する場合のみ追加
      .setHeader("User-Agent", "Mozilla/5.0")
      .build(),
    contentDescription = contentDescription,
    contentScale = ContentScale.Crop,
  )
}
