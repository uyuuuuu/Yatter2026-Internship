package com.dmm.bootcamp.yatter.ui.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmm.bootcamp.yatter.ui.theme.YatterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTemplate(
  userName: String,
  onChangedUserName: (String) -> Unit,
  password: String,
  onChangedPassword: (String) -> Unit,
  isEnableLogin: Boolean,
  isLoading: Boolean,
  onClickLogin: () -> Unit,
  onClickRegister: () -> Unit,
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "ログイン",
            color = MaterialTheme.colorScheme.onPrimary,
          )        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primary
        )
      )
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(8.dp)
    ){
      Column(modifier = Modifier.fillMaxSize()){
        Text(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp),
          fontSize = MaterialTheme.typography.bodyLarge.fontSize,
          text = "ユーザー名"
        )
        OutlinedTextField(
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
          shape = MaterialTheme.shapes.extraLarge,
          value = userName,
          onValueChange = onChangedUserName,
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
          text = "パスワード"
        )
        OutlinedTextField(
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
          shape = MaterialTheme.shapes.extraLarge,
          value = password,
          onValueChange = onChangedPassword,
          placeholder = {
            Text(
              text = "password",
              color = MaterialTheme.colorScheme.outline
            )
          }
        )
        Button(
          enabled = isEnableLogin,
          onClick = onClickLogin,
          modifier = Modifier
            .fillMaxWidth(),
        ) {
          Text(text = "ログイン")
        }

        HorizontalDivider(
          modifier = Modifier.padding(vertical = 16.dp),
          thickness = DividerDefaults.Thickness,
          color = DividerDefaults.color
        )

        Text(
          text = "はじめてご利用の方は",
          modifier = Modifier.fillMaxWidth(),
          textAlign = TextAlign.Center,
          style = MaterialTheme.typography.bodyMedium
        )
        TextButton(
          onClick = onClickRegister,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(text = "新規ユーザー登録")
        }
      }
      if (isLoading) {
        CircularProgressIndicator()
      }
    }
  }
}

@Preview(
  name = "Light",
  uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
  name = "Dark",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun LoginTemplatePreview() {
  YatterTheme {
    Surface {
      LoginTemplate(
        userName = "",
        onChangedUserName = {},
        password = "",
        onChangedPassword = {},
        isEnableLogin = true,
        isLoading = false,
        onClickLogin = {},
        onClickRegister = {},
      )
    }
  }
}
