package com.dmm.bootcamp.yatter.ui.timeline.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dmm.bootcamp.yatter.ui.theme.YatterTheme

@Composable
fun LogoutButton(
  onClick: () -> Unit,
) {
  IconButton(
    onClick = onClick,
    colors = IconButtonDefaults.iconButtonColors(
      contentColor = MaterialTheme.colorScheme.onPrimary
    )
  ) {
    Icon(
      imageVector = Icons.AutoMirrored.Filled.ExitToApp,
      contentDescription = "post"
    )
  }
}

@Preview
@Composable
fun LogoutButtonPreview(){
  YatterTheme{
    Surface{
      LogoutButton(
        onClick = {}
      )
    }
  }
}
