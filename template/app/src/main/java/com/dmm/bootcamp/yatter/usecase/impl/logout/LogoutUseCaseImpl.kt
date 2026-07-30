package com.dmm.bootcamp.yatter.usecase.impl.logout

import com.dmm.bootcamp.yatter.domain.service.LogoutService
import com.dmm.bootcamp.yatter.infra.pref.LoginUserPreferences
import com.dmm.bootcamp.yatter.usecase.logout.LogoutUseCase

internal class LogoutUseCaseImpl(
  private val logoutService: LogoutService,
  private val loginUserPreferences: LoginUserPreferences,
) : LogoutUseCase {
  override suspend fun execute() {
    logoutService.execute()
    loginUserPreferences.clear()
  }
}
