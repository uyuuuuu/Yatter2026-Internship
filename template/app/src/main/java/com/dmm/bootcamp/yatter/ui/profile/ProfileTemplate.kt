package com.dmm.bootcamp.yatter.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmm.bootcamp.yatter.R
import com.dmm.bootcamp.yatter.ui.component.YatterAvatarIcon
import com.dmm.bootcamp.yatter.ui.component.YatterBackButton
import com.dmm.bootcamp.yatter.ui.component.YatterTopAppBar
import com.dmm.bootcamp.yatter.ui.profile.bindingmodel.ProfileBindingModel
import com.dmm.bootcamp.yatter.ui.theme.YatterTheme
import com.dmm.bootcamp.yatter.ui.timeline.bindingmodel.YweetBindingModel
import com.dmm.bootcamp.yatter.ui.timeline.component.YweetRow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileTemplate(
  bindingModel: ProfileBindingModel,
  isMyUser: Boolean,
  onClickUpdateUser: () -> Unit,
  onClickYweet: (id: String) -> Unit,
  onClickNavIcon: () -> Unit,
  isLoading: Boolean,
  isRefreshing: Boolean,
  onRefresh: () -> Unit,
) {
  val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh)
  Scaffold(
    topBar = {
      YatterTopAppBar(
        title = "プロフィール",
        navigationIcon = { YatterBackButton(onClickNavIcon) }
      )
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .pullRefresh(pullRefreshState), // refresh検知
      contentAlignment = Alignment.Center,
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(8.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          verticalAlignment = Alignment.CenterVertically,
          ) {
          YatterAvatarIcon(
            avatar = bindingModel.avatar,
            modifier = Modifier.size(72.dp),
            contentDescription = stringResource(id = R.string.public_timeline_avatar_content_description),
          )

          if (isMyUser) {
            Spacer(modifier = Modifier.weight(1f))
            Button(
              onClickUpdateUser,
            ) { Text("プロフィールを編集") }
          }
        }
        Text(
          text = bindingModel.displayName.orEmpty(),
          fontWeight = FontWeight.Bold, // 文字を太字に
        )
        Text(
          text = "@${bindingModel.username}",
          color = MaterialTheme.colorScheme.onBackground.copy(ContentAlpha.medium),
          fontWeight = FontWeight.Bold, // 文字を太字に
        )
        if(bindingModel.note != null){
          Text(
            modifier = Modifier.padding(top=4.dp),
            text = bindingModel.note
          )
        }
        HorizontalDivider(
          modifier = Modifier.padding(vertical = 16.dp),
          thickness = DividerDefaults.Thickness,
          color = DividerDefaults.color
        )
        LazyColumn(
          modifier = Modifier
            .fillMaxSize(),
          verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          items(bindingModel.yweetList) { item ->
            YweetRow(
              yweetBindingModel = item,
              onClickYweet = {onClickYweet(item.id)}
            )
          }
        }
      }
      // refreshのぐるぐる
      PullRefreshIndicator(
        refreshing = isRefreshing,
        state = pullRefreshState,
        modifier = Modifier.align(Alignment.TopCenter)
      )
      if (isLoading) {
        CircularProgressIndicator()
      }
    }
  }
}

@Preview
@Composable
private fun ProfileTemplatePreview() {
  YatterTheme {
    Surface {
      ProfileTemplate(
        bindingModel = ProfileBindingModel(
          username = "username",
          displayName = null,
          note = null,
          avatar = null,
          yweetList = listOf(
            YweetBindingModel(
              id = "id1",
              displayName = "display name1",
              username = "username1",
              avatar = null,
              content = "preview content1",
              attachmentImageList = listOf()
            ),
            YweetBindingModel(
              id = "id2",
              displayName = "display name2",
              username = "username2",
              avatar = null,
              content = "preview content2",
              attachmentImageList = listOf()
            ),
            YweetBindingModel(
              id = "id3",
              displayName = "display name3",
              username = "username3",
              avatar = null,
              content = "preview content3",
              attachmentImageList = listOf()
            ),
            YweetBindingModel(
              id = "id3",
              displayName = "display name3",
              username = "username3",
              avatar = null,
              content = "preview content3",
              attachmentImageList = listOf()
            ),
            YweetBindingModel(
              id = "id3",
              displayName = "display name3",
              username = "username3",
              avatar = null,
              content = "preview content3",
              attachmentImageList = listOf()
            ),
            YweetBindingModel(
              id = "id3",
              displayName = "display name3",
              username = "username3",
              avatar = null,
              content = "preview content3",
              attachmentImageList = listOf()
            ),
            YweetBindingModel(
              id = "id3",
              displayName = "display name3",
              username = "username3",
              avatar = null,
              content = "preview content3",
              attachmentImageList = listOf()
            ),
            YweetBindingModel(
              id = "id3",
              displayName = "display name3",
              username = "username3",
              avatar = null,
              content = "preview content3",
              attachmentImageList = listOf()
            ),
            YweetBindingModel(
              id = "id3",
              displayName = "display name3",
              username = "username3",
              avatar = null,
              content = "preview content3",
              attachmentImageList = listOf()
            ),
            YweetBindingModel(
              id = "id3",
              displayName = "display name3",
              username = "username3",
              avatar = null,
              content = "preview content3",
              attachmentImageList = listOf()
            ),
            YweetBindingModel(
              id = "id3",
              displayName = "display name3",
              username = "username3",
              avatar = null,
              content = "preview content3",
              attachmentImageList = listOf()
            ),YweetBindingModel(
              id = "id3",
              displayName = "display name3",
              username = "username3",
              avatar = null,
              content = "preview content3",
              attachmentImageList = listOf()
            ),
            YweetBindingModel(
              id = "id3",
              displayName = "display name3",
              username = "username3",
              avatar = null,
              content = "preview content3",
              attachmentImageList = listOf()
            ),
            YweetBindingModel(
              id = "id3",
              displayName = "display name3",
              username = "username3",
              avatar = null,
              content = "preview content3",
              attachmentImageList = listOf()
            ),

          ),
        ),
        isMyUser = true,
        onClickUpdateUser = {},
        onClickYweet = {},
        onClickNavIcon = {},
        isLoading = false,
        isRefreshing = false,
        onRefresh = {},
      )
    }
  }
}
