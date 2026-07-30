package com.dmm.bootcamp.yatter.di

import com.dmm.bootcamp.yatter.domain.service.LoginService
import com.dmm.bootcamp.yatter.domain.service.LogoutService
import com.dmm.bootcamp.yatter.infra.domain.service.LoginServiceImpl
import com.dmm.bootcamp.yatter.usecase.impl.login.LoginUseCaseImpl
import com.dmm.bootcamp.yatter.usecase.impl.logout.LogoutUseCaseImpl
import com.dmm.bootcamp.yatter.usecase.impl.post.PostYweetUseCaseImpl
import com.dmm.bootcamp.yatter.usecase.impl.register.RegisterUserUseCaseImpl
import com.dmm.bootcamp.yatter.usecase.login.LoginUseCase
import com.dmm.bootcamp.yatter.usecase.logout.LogoutUseCase
import com.dmm.bootcamp.yatter.usecase.post.PostYweetUseCase
import com.dmm.bootcamp.yatter.usecase.register.RegisterUserUseCase
import org.koin.dsl.module

internal val useCaseModule = module {
  factory<PostYweetUseCase> { PostYweetUseCaseImpl(get(), get()) }
  factory<RegisterUserUseCase> { RegisterUserUseCaseImpl(get(), get(), get()) }
  factory<LoginUseCase> { LoginUseCaseImpl(get(), get()) }
  factory<LogoutUseCase> { LogoutUseCaseImpl(get(), get()) }
}
