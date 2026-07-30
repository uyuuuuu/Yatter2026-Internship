package com.dmm.bootcamp.yatter.usecase.update

sealed class UpdateUserUseCaseResult {
  object Success : UpdateUserUseCaseResult()
  sealed class Failure : UpdateUserUseCaseResult() {
    object InvalidImage : Failure()
    data class UpdateUserError(val throwable: Throwable) : Failure()
  }
}
