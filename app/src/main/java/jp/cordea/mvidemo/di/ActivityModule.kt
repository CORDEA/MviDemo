package jp.cordea.mvidemo.di

import dagger.Module
import jp.cordea.mvidemo.ui.login.LoginActivityModule
import jp.cordea.mvidemo.ui.main.MainActivityModule

@Module(includes = [
    MainActivityModule::class,
    LoginActivityModule::class
])
class ActivityModule
