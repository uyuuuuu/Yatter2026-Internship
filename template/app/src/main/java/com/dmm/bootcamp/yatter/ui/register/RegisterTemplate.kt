package com.dmm.bootcamp.yatter.ui.register

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmm.bootcamp.yatter.ui.component.YatterBackButton
import com.dmm.bootcamp.yatter.ui.component.YatterTopAppBar
import com.dmm.bootcamp.yatter.ui.theme.YatterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterTemplate (
  userName: String,
  onChangedUserName: (String) -> Unit,
  password: String,
  onChangedPassword: (String) -> Unit,
  isEnableRegister: Boolean,
  isLoading: Boolean,
  errorMessage: String?,
  onClickRegister: () -> Unit,
  onClickLogin: () -> Unit,) {

  Scaffold(
    topBar = {
      YatterTopAppBar(
        title = "新規登録",
        navigationIcon = { YatterBackButton(onClickLogin) }
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
            .padding(top = 16.dp),
          fontSize = MaterialTheme.typography.bodyLarge.fontSize,
          text = "パスワード"
        )
        Text(
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
          color = MaterialTheme.colorScheme.outline,
          fontSize = MaterialTheme.typography.bodySmall.fontSize,
          text = "（8文字以上かつ大文字・小文字・記号をそれぞれ1文字以上）"
        )
        OutlinedTextField(
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth(),
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
        Text(
          modifier = Modifier.padding(2.dp),
          text = errorMessage.orEmpty(),
          color = MaterialTheme.colorScheme.error
        )
        Button(
          enabled = isEnableRegister,
          onClick = onClickRegister,
          modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
        ) {
          Text("登録")
        }
        HorizontalDivider(
          modifier = Modifier.padding(vertical = 16.dp),
          thickness = DividerDefaults.Thickness,
          color = DividerDefaults.color
        )

        Text(
          text = "または",
          modifier = Modifier.fillMaxWidth(),
          textAlign = TextAlign.Center,
          style = MaterialTheme.typography.bodyMedium
        )
        TextButton(
          onClick = onClickLogin,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(text = "ログイン")
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
private fun RegisterTemplatePreview() {
  YatterTheme {
    Surface {
      RegisterTemplate(
        userName = "",
        onChangedUserName = {},
        password = "",
        onChangedPassword = {},
        isEnableRegister = true,
        isLoading = false,
        errorMessage = "パスワードの形式が正しくありません",
        onClickRegister = {},
        onClickLogin = {},
      )
    }
  }
}
