package jp.cordea.mvidemo.ui.login

import android.support.v4.app.FragmentActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import jp.cordea.mvidemo.di.ActivityScope
import jp.cordea.mvidemo.di.ViewModelModule

@Module
interface LoginActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        LoginNavigatorModule::class,
        LoginViewModelModule::class
    ])
    fun contributeLoginActivity(): LoginActivity

    @Binds
    fun bindActivity(activity: LoginActivity): FragmentActivity
}

@Module
class LoginViewModelModule : ViewModelModule<LoginViewModel>(LoginViewModel::class)
