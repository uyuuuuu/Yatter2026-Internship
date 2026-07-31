package com.dmm.bootcamp.yatter.ui.updateuser.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmm.bootcamp.yatter.R
import com.dmm.bootcamp.yatter.ui.component.YatterAvatarIcon
import com.dmm.bootcamp.yatter.ui.theme.YatterTheme
import java.io.File

@Composable
fun ProfileFormContents(
  displayName: String?,
  onChangedDisplayName: (String) -> Unit,
  note: String?,
  onChangedNote: (String) -> Unit,
  avatar: Any?,
  onClickSelectImage: () -> Unit,
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
  Box(
    modifier = Modifier
      .size(96.dp)
      .clip(CircleShape)
      .clickable(onClick = onClickSelectImage),
    contentAlignment = Alignment.Center
  ) {
    YatterAvatarIcon(
      avatar = avatar,
      modifier = Modifier
        .size(96.dp),
      contentDescription = stringResource(id = R.string.public_timeline_avatar_content_description),
    )
    if (avatar == null) {
      Icon(
        imageVector = Icons.Default.Add,
        contentDescription = "post",
        modifier = Modifier
          .size(96.dp)
          .border(BorderStroke(1.dp, MaterialTheme.colorScheme.outline), CircleShape)
          .background(MaterialTheme.colorScheme.background.copy(ContentAlpha.medium), CircleShape)
          .padding(12.dp),
        tint = MaterialTheme.colorScheme.outline
      )
    }
  }
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
          onClickSelectImage = {},
        )
      }
    }
  }
}
