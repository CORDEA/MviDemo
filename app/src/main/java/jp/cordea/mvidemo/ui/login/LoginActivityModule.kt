package jp.cordea.mvidemo.ui.login

import dagger.Module
import dagger.android.ContributesAndroidInjector
import jp.cordea.mvidemo.di.ActivityScope

@Module
interface LoginActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        LoginNavigatorModule::class
    ])
    fun contributeLoginActivity(): LoginActivity
}
