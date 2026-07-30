package com.dmm.bootcamp.yatter.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun YatterBackButton (
  onClickNavIcon: () -> Unit,
) {
  IconButton(onClickNavIcon) {
    Icon(
      imageVector = Icons.AutoMirrored.Filled.ArrowBack,
      contentDescription = "戻る",
      tint = MaterialTheme.colorScheme.onPrimary,
    )
  }
}
