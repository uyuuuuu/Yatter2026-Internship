package com.dmm.bootcamp.yatter.ui.updateuser

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import java.io.File

@Composable
fun FirstUpdateUserTemplate(
  displayName: String?,
  onChangedDisplayName: (String) -> Unit,
  note: String?,
  onChangedNote: (String) -> Unit,
  avatar: File?,
  onChangedAvatar: (File) -> Unit,
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
        Text(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp),
          fontSize = MaterialTheme.typography.bodyLarge.fontSize,
          text = "表示名"
        )
        OutlinedTextField(
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
          shape = MaterialTheme.shapes.extraLarge,
          value = displayName.orEmpty(),
          onValueChange = onChangedDisplayName,
          placeholder = {
            Text(
              text = "username",
              color = MaterialTheme.colorScheme.outline
            )
          }
        )
        Text(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp),
          fontSize = MaterialTheme.typography.bodyLarge.fontSize,
          text = "自己紹介"
        )
        OutlinedTextField(
          minLines = 5,
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
          shape = MaterialTheme.shapes.extraLarge,
          value = note.orEmpty(),
          onValueChange = onChangedNote,
          placeholder = {
            Text(
              text = "description",
              color = MaterialTheme.colorScheme.outline
            )
          }
        )
//        Text(
//          modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 16.dp, bottom = 8.dp),
//          fontSize = MaterialTheme.typography.bodyLarge.fontSize,
//          text = "アイコン画像"
//        )
        Button(
          onClick = onClickRegister,
          modifier = Modifier
            .fillMaxWidth()
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
        onChangedAvatar = {},
        isLoading = false,
        onClickRegister = {},
        onClickSkip = {},
      )
    }
  }
}
