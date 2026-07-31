package com.dmm.bootcamp.yatter.ui.updateuser.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmm.bootcamp.yatter.ui.theme.YatterTheme
import java.io.File

@Composable
fun ProfileFormContents(
  displayName: String?,
  onChangedDisplayName: (String) -> Unit,
  note: String?,
  onChangedNote: (String) -> Unit,
  avatar: Any?,
  onChangedAvatar: (File) -> Unit,
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
  Text(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 16.dp, bottom = 8.dp),
    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
    text = "アイコン画像"
  )
}

@Preview
@Composable
fun ProfileFormPreview() {
  YatterTheme {
    Surface {
      Column {
        ProfileFormContents(
          displayName = "表示名",
          onChangedDisplayName = {},
          note = "自己紹介",
          onChangedNote = {},
          avatar = null,
          onChangedAvatar = {}
        )
      }
    }
  }
}
