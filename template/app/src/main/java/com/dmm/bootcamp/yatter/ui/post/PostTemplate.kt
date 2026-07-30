package com.dmm.bootcamp.yatter.ui.post

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmm.bootcamp.yatter.R
import com.dmm.bootcamp.yatter.ui.component.YatterAvatarIcon
import com.dmm.bootcamp.yatter.ui.component.YatterBackButton
import com.dmm.bootcamp.yatter.ui.component.YatterTopAppBar
import com.dmm.bootcamp.yatter.ui.post.bindingmodel.PostBindingModel
import com.dmm.bootcamp.yatter.ui.theme.YatterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostTemplate(
  postBindingModel: PostBindingModel,
  isLoading: Boolean,
  canPost: Boolean,
  onYweetTextChanged: (String) -> Unit,
  onClickPost: () -> Unit,
  onClickNavIcon: () -> Unit,
) {
  Scaffold(
    topBar = {
      YatterTopAppBar(
        title = "投稿",
        navigationIcon = { YatterBackButton(onClickNavIcon) }
      )
    }

  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      contentAlignment = Alignment.Center
    ) {
      Row(
        modifier = Modifier
          .fillMaxSize(),
      ) {
        YatterAvatarIcon(
          avatar = postBindingModel.avatarUrl,
          modifier = Modifier.size(64.dp),
          contentDescription = stringResource(id = R.string.public_timeline_avatar_content_description),
        )
        Column(
          horizontalAlignment = Alignment.End
        ) {
          TextField(
            modifier = Modifier
              .fillMaxWidth()
              .weight(1f), //余り部分目一杯広げる
            value = postBindingModel.yweetText,
            onValueChange = onYweetTextChanged,
            colors = TextFieldDefaults.colors(
              focusedContainerColor = Color.Transparent,
              unfocusedContainerColor = Color.Transparent,
              focusedIndicatorColor = Color.Transparent,
              unfocusedIndicatorColor = Color.Transparent,
              disabledIndicatorColor = Color.Transparent,
            ),
            placeholder = {
              Text(
                text = "今何してる？",
                color = MaterialTheme.colorScheme.secondary
              )
            },
          )
          Button(
            onClick = onClickPost,
            modifier = Modifier.padding(16.dp),
            enabled = canPost,
          ) {
            Text(text = "ツイート")
          }
        }
      }

      if (isLoading) {
        CircularProgressIndicator()
      }
    }
  }
}

@Preview
@Composable
private fun PostTemplatePreview() {
  YatterTheme {
    Surface() {
      PostTemplate(
        postBindingModel = PostBindingModel(
          avatarUrl = "https://avatars.githubusercontent.com/u/19385268?v=4",
          yweetText = "",
          attachmentImageUris = emptyList(),
        ),
        isLoading = false,
        canPost = false,
        onYweetTextChanged = {},
        onClickPost = {},
        onClickNavIcon = {},
      )
    }
  }
}
