package com.dmm.bootcamp.yatter

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dmm.bootcamp.yatter.ui.MainApp
import com.dmm.bootcamp.yatter.ui.theme.YatterTheme

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)

    setContent {
      YatterTheme {
        MainApp()
      }
    }
  }
}
