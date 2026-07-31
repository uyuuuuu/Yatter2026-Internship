package com.dmm.bootcamp.yatter.ui.updateuser

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmm.bootcamp.yatter.ui.component.YatterBackButton
import com.dmm.bootcamp.yatter.ui.component.YatterTopAppBar
import com.dmm.bootcamp.yatter.ui.theme.YatterTheme
import com.dmm.bootcamp.yatter.ui.updateuser.component.ProfileFormContents
import java.io.File

@Composable
fun UpdateUserTemplate(
  displayName: String?,
  onChangedDisplayName: (String) -> Unit,
  note: String?,
  onChangedNote: (String) -> Unit,
  avatar: Any?,
  onChangedAvatar: (File) -> Unit,
  isLoading: Boolean,
  onClickUpdate: () -> Unit,
  onClickNavIcon: () -> Unit,
) {

  Scaffold(
    topBar = {
      YatterTopAppBar(
        title = "プロフィール編集",
        navigationIcon = { YatterBackButton(onClickNavIcon) }
      )
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(8.dp),
      contentAlignment = Alignment.Center
    ){
      Column(
        modifier = Modifier
          .fillMaxSize()
      ) {
        ProfileFormContents(
          displayName = displayName,
          onChangedDisplayName = onChangedDisplayName,
          note = note,
          onChangedNote = onChangedNote,
          avatar = avatar,
          onChangedAvatar = onChangedAvatar
        )
        Button(
          onClick = onClickUpdate,
          modifier = Modifier
            .fillMaxWidth()
        ) {
          Text("保存")
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
private fun UpdateUserTemplatePreview() {
  YatterTheme {
    Surface {
      UpdateUserTemplate (
        displayName = null,
        onChangedDisplayName = {},
        note = null,
        onChangedNote = {},
        avatar = null,
        onChangedAvatar = {},
        isLoading = false,
        onClickUpdate = {},
        onClickNavIcon = {},
      )
    }
  }
}
