package com.dmm.bootcamp.yatter.usecase.update

import com.dmm.bootcamp.yatter.domain.model.User
import java.io.File

interface UpdateUserUseCase {
  suspend fun execute(me: User, displayName: String?, note: String?, avatar: File?): UpdateUserUseCaseResult
}
