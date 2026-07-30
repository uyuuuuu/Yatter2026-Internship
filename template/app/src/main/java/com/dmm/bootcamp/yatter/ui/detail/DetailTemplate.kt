package com.dmm.bootcamp.yatter.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dmm.bootcamp.yatter.R
import com.dmm.bootcamp.yatter.ui.component.YatterAvatarIcon
import com.dmm.bootcamp.yatter.ui.component.YatterBackButton
import com.dmm.bootcamp.yatter.ui.component.YatterTopAppBar
import com.dmm.bootcamp.yatter.ui.detail.bindingmodel.DetailBindingModel
import com.dmm.bootcamp.yatter.ui.theme.YatterTheme

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailTemplate(
  yweet: DetailBindingModel,
  onClickNavIcon: () -> Unit,
  isLoading: Boolean,
) {
  Scaffold(
    topBar = {
      YatterTopAppBar(
        title = "詳細",
        navigationIcon = { YatterBackButton(onClickNavIcon) },
      )
    }
  ) { paddingValues ->

    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      contentAlignment = Alignment.Center,
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(8.dp)
      ) {
        Row( // 横並び
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          YatterAvatarIcon(
            avatar = yweet.avatar,
            modifier = Modifier.size(48.dp),
            contentDescription = stringResource(id = R.string.public_timeline_avatar_content_description),
          )

          Column() {
            Text(
              text = yweet.displayName,
              maxLines = 1, // 文字列が複数行にならないように指定
              overflow = TextOverflow.Ellipsis, // はみ出した分を「...」で表現
              fontWeight = FontWeight.Bold, // 文字を太字に
            )
            Text(
              text = "@${yweet.username}",
              color = MaterialTheme.colorScheme.onBackground.copy(ContentAlpha.medium),
              maxLines = 1, // 文字列が複数行にならないように指定
              overflow = TextOverflow.Ellipsis, // はみ出した分を「...」で表現
              fontWeight = FontWeight.Bold, // 文字を太字に
            )
          }

        }
        Text(text = yweet.content)
        LazyRow {
          // itemsの第一引数に並べたいデータセットを渡す
          items(yweet.attachmentImageList) { attachmentImage ->
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
      // 画面読み込みのぐるぐる
      if (isLoading) {
        CircularProgressIndicator()
      }
    }
  }
}

@Preview
@Composable
private fun DetailTemplatePreview() {
  YatterTheme {
    Surface {
      DetailTemplate(
        yweet = DetailBindingModel(
          id = "id1",
          displayName = "display name1",
          username = "username1",
          avatar = null,
          content = "preview content1",
          attachmentImageList = listOf()
        ),
        onClickNavIcon = {},
        isLoading = false,
      )
    }
  }
}
