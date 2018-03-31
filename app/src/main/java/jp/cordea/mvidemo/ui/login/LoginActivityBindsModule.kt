package jp.cordea.mvidemo.ui.login

import android.app.Activity
import android.arch.lifecycle.ViewModelStoreOwner
import dagger.Binds
import dagger.Module

@Module
interface LoginActivityBindsModule {

    @Binds
    fun bindActivity(activity: LoginActivity): Activity

    @Binds
    fun bindViewModelStoreOwner(activity: LoginActivity): ViewModelStoreOwner
}
