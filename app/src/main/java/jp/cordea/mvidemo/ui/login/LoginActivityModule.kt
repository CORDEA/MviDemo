package jp.cordea.mvidemo.ui.login

import dagger.Module
import dagger.android.ContributesAndroidInjector
import jp.cordea.mvidemo.di.ActivityScope
import jp.cordea.mvidemo.di.ViewModelModule

@Module
interface LoginActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        LoginActivityBindsModule::class,
        LoginViewModelModule::class
    ])
    fun contributeLoginActivity(): LoginActivity
}

@Module
class LoginViewModelModule : ViewModelModule<LoginViewModel>(LoginViewModel::class)
