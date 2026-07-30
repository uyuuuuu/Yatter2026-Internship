package com.dmm.bootcamp.yatter.ui

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.dmm.bootcamp.yatter.ui.login.LoginPage
import com.dmm.bootcamp.yatter.ui.navigation.LoginKey
import com.dmm.bootcamp.yatter.ui.navigation.PostKey
import com.dmm.bootcamp.yatter.ui.navigation.PublicTimelineKey
import com.dmm.bootcamp.yatter.ui.navigation.RegisterKey
import com.dmm.bootcamp.yatter.ui.navigation.YatterNavKey
import com.dmm.bootcamp.yatter.ui.post.PostPage
import com.dmm.bootcamp.yatter.ui.register.RegisterPage
import com.dmm.bootcamp.yatter.ui.timeline.PublicTimelinePage
import org.koin.androidx.compose.koinViewModel
@Composable
fun MainApp(mainViewModel: MainViewModel = koinViewModel()) {
  val isLoggedIn by mainViewModel.isLoggedIn.collectAsStateWithLifecycle()
  LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) { mainViewModel.onCreate() }

  // ログイン状態で初期画面分岐
  val startNavKey: YatterNavKey = when (isLoggedIn) {
    true -> PublicTimelineKey
    false -> LoginKey
    null -> return
  }

  key(startNavKey) {
    val backStack = rememberNavBackStack(startNavKey)
    val onBack: () -> Unit = {
      if (backStack.size > 1) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
          backStack.removeLast()
        } else {
          backStack.removeAt(backStack.lastIndex)
        }
      }
    }
    NavDisplay(
      backStack = backStack,
      onBack = onBack,
      entryDecorators = listOf(
        rememberSaveableStateHolderNavEntryDecorator(),
        rememberViewModelStoreNavEntryDecorator(),
      ),
      entryProvider = entryProvider {
        entry<LoginKey> {
          LoginPage(
            onLoggedIn = {
              backStack.clear()
              backStack.add(PublicTimelineKey)
            },
            onNavigateToRegister = { backStack.add(RegisterKey) },
          )
        }
        entry<PublicTimelineKey> {
          PublicTimelinePage(
            onNavigateToPost = { backStack.add(PostKey) },
            onNavigateToLogin = {
              backStack.clear()
              backStack.add(LoginKey)
            }
          )
        }
        entry<PostKey> {
          PostPage(
            onPosted = { onBack() },
            onBack = onBack,
          )
        }
        entry<RegisterKey> {
          RegisterPage(
            onRegistered = { backStack.add(PublicTimelineKey) },
            onNavigatedToLogin = { backStack.add(LoginKey) },
          )
        }
        // ほかの画面キーも同様に entry を追加
      },
    )
  }
}
