package com.dmm.bootcamp.yatter.ui.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dmm.bootcamp.yatter.R
import com.dmm.bootcamp.yatter.ui.theme.YatterTheme
import com.dmm.bootcamp.yatter.ui.timeline.bindingmodel.ImageBindingModel
import com.dmm.bootcamp.yatter.ui.timeline.bindingmodel.YweetBindingModel

/**
 * タイムラインに表示する1件分のYweetを描画
 *
 * @param yweetBindingModel 画面表示用のYweetデータ
 */

@Composable
fun YweetRow(
  yweetBindingModel: YweetBindingModel,
  modifier: Modifier = Modifier,
) {
  Row( // 横並び
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    val context = LocalContext.current

    // プレイスホルダー画像の生成
    // アイコン未設定状態
    val placeholder = ResourcesCompat.getDrawable(
      context.resources,
      R.drawable.avatar_placeholder,
      null,
    )

    AsyncImage( // Coinライブラリ
      modifier = Modifier.size(48.dp),
      // ImageRequestを作成して、
      // 画像取得できていない状態のプレイスホルダー設定
      model = ImageRequest.Builder(context)
        .data(yweetBindingModel.avatar)
        .placeholder(placeholder)
        .error(placeholder)
        .fallback(placeholder)
        // モックサーバーから画像取得する場合のみ追加
        .setHeader("User-Agent", "Mozilla/5.0")
        .build(),
      contentDescription = stringResource(id = R.string.public_timeline_avatar_content_description),
      contentScale = ContentScale.Crop,
    )

    // 表示名@user、Yweet文を縦に
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
      // 表示名
      Text(
        text = buildAnnotatedString {
          append(yweetBindingModel.displayName)
          withStyle(
            style = SpanStyle(
              // 文字色を薄くするために、ContentAlpha.mediumを指定
              color = MaterialTheme.colorScheme.onBackground.copy(ContentAlpha.medium)
            )
          ) {
            append(" @${yweetBindingModel.username}")
          }
        },
        maxLines = 1, // 文字列が複数行にならないように指定
        overflow = TextOverflow.Ellipsis, // はみ出した分を「...」で表現
        fontWeight = FontWeight.Bold, // 文字を太字に
      )
      // 本文
      Text(text = yweetBindingModel.content)

      // 添付画像
      LazyRow {
        // itemsの第一引数に並べたいデータセットを渡す
        items(yweetBindingModel.attachmentImageList) { attachmentImage ->
          // データ1件あたりに表示したいコンポーザブルを呼び出す
          AsyncImage(
            model = attachmentImage.url,
            contentDescription = attachmentImage.description,
            modifier = Modifier.fillParentMaxWidth(1f),
            contentScale = ContentScale.Fit,
          )
          Spacer(modifier = Modifier.width(4.dp))
        }
      }
    }
  }
}

@Preview
@Composable
private fun YweetRowPreview() {
  YatterTheme {
    Surface {
      YweetRow(
        yweetBindingModel = YweetBindingModel(
          id = "id",
          displayName = "mito",
          username = "mitohato14",
          avatar = "https://avatars.githubusercontent.com/u/19385268?v=4",
          content = "preview content",
          attachmentImageList = listOf(
            ImageBindingModel(
              id = "id",
              type = "image",
              url = "https://avatars.githubusercontent.com/u/39693306?v=4",
              description = "icon"
            )
          )
        )
      )
    }
  }
}
