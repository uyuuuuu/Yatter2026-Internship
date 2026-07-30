package com.dmm.bootcamp.yatter.infra.domain.service

import com.dmm.bootcamp.yatter.domain.service.LogoutService
import com.dmm.bootcamp.yatter.infra.pref.TokenPreferences

class LogoutServiceImpl(
  private val tokenPreferences: TokenPreferences,
): LogoutService {
  override suspend fun execute() {
    tokenPreferences.clear()
  }
}
