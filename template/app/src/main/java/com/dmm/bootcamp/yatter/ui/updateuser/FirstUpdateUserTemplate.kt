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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmm.bootcamp.yatter.ui.component.YatterTopAppBar
import com.dmm.bootcamp.yatter.ui.theme.YatterTheme
import com.dmm.bootcamp.yatter.ui.updateuser.component.ProfileFormContents
import java.io.File

@Composable
fun FirstUpdateUserTemplate(
  displayName: String?,
  onChangedDisplayName: (String) -> Unit,
  note: String?,
  onChangedNote: (String) -> Unit,
  avatar: File?,
  onClickSelectImage: () -> Unit,
  isLoading: Boolean,
  onClickRegister: () -> Unit,
  onClickSkip: () -> Unit,
) {

  Scaffold(
    topBar = {
      YatterTopAppBar(
        title = "プロフィール登録",
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
          onClickSelectImage = onClickSelectImage,
        )
        Button(
          onClick = onClickRegister,
          modifier = Modifier
            .fillMaxWidth()
            .padding(top=12.dp)
        ) {
          Text("登録")
        }
        TextButton(
          onClick = onClickSkip,
          modifier = Modifier
            .fillMaxWidth()
            .padding(top=8.dp)
        ) {
          Text("後で登録する")
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
private fun FirstUpdateUserTemplatePreview() {
  YatterTheme {
    Surface {
      FirstUpdateUserTemplate (
        displayName = null,
        onChangedDisplayName = {},
        note = null,
        onChangedNote = {},
        avatar = null,
        isLoading = false,
        onClickRegister = {},
        onClickSelectImage = {},
        onClickSkip = {},
      )
    }
  }
}
