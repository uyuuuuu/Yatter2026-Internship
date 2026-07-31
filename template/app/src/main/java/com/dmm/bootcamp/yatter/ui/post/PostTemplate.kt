package com.dmm.bootcamp.yatter.ui.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
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
  onClickSelectImage: () -> Unit,
  onClickPost: () -> Unit,
  onClickNavIcon: () -> Unit,
) {
  Scaffold(
    topBar = {
      YatterTopAppBar(
        title = "投稿",
        navigationIcon = { YatterBackButton(onClickNavIcon) },
        actions = {
          Button(
            onClick = onClickPost,
            enabled = canPost,
            modifier = Modifier.height(36.dp).padding(horizontal = 4.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(
              horizontal = 16.dp,
              vertical = 0.dp,
            ),
            colors = ButtonDefaults.buttonColors(
              containerColor = MaterialTheme.colorScheme.primaryContainer,
              contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
              disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
              disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                alpha = 0.38f,
              ),
            ),
          ) {
            Text(
              text = "ツイート",
              fontSize = MaterialTheme.typography.bodyLarge.fontSize,
              maxLines = 1
            )
          }
        }
      )
    }

  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(4.dp),
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
        Box {
          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState())
              .padding(bottom = 80.dp),
          ) {
            // 本文
            TextField(
              modifier = Modifier
                .fillMaxWidth(),
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
            LazyRow(
              modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
              contentPadding = PaddingValues(horizontal = 8.dp),
              horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
              items(postBindingModel.attachmentImageUris) { item ->
                AsyncImage(
                  modifier = Modifier
                    .fillParentMaxHeight()
                    .padding(vertical = 4.dp),
                  model = item,
                  contentDescription = "添付画像",
                  contentScale = ContentScale.FillHeight,
                )
              }
            }
          }
          FilledIconButton(
            onClick = onClickSelectImage,
            modifier = Modifier
              .align(Alignment.BottomEnd)
              .padding(8.dp)
              .size(60.dp)
              .shadow(
                elevation = 2.dp,
                shape = CircleShape,
              ),
            shape = CircleShape,
            colors = IconButtonDefaults.filledIconButtonColors(
              containerColor = MaterialTheme.colorScheme.primaryContainer,
              contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
          ) {
            Icon(
              imageVector = Icons.Default.Image,
              contentDescription = "画像を追加",
              modifier = Modifier.size(32.dp),
            )
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
        onClickSelectImage = {},
        onClickPost = {},
        onClickNavIcon = {},
      )
    }
  }
}
