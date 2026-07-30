package com.dmm.bootcamp.yatter.usecase.impl.update

import com.dmm.bootcamp.yatter.domain.model.User
import com.dmm.bootcamp.yatter.domain.repository.UserRepository
import com.dmm.bootcamp.yatter.usecase.update.UpdateUserUseCase
import com.dmm.bootcamp.yatter.usecase.update.UpdateUserUseCaseResult
import java.io.File

class UpdateUserUseCaseImpl (
  private val userRepository: UserRepository,
  ) : UpdateUserUseCase {
    override suspend fun execute(me: User, displayName: String?, note: String?, avatar: File?): UpdateUserUseCaseResult {


      runCatching {
        val updateMe = userRepository.update(
          me = me,
          newDisplayName = displayName,
          newNote = note,
          newAvatar = avatar,
          newHeader = null,
        )
      }.onFailure {
        return UpdateUserUseCaseResult.Failure.UpdateUserError(it)
      }

      return UpdateUserUseCaseResult.Success
    }
  }
