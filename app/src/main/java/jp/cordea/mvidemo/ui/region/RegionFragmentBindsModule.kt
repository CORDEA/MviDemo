package jp.cordea.mvidemo.ui.region

import android.arch.lifecycle.ViewModelStoreOwner
import android.support.v4.app.Fragment
import dagger.Binds
import dagger.Module

@Module
interface RegionFragmentBindsModule {

    @Binds
    fun bindFragment(fragment: RegionFragment): Fragment

    @Binds
    fun bindViewModelStoreOwner(fragment: RegionFragment): ViewModelStoreOwner
}
