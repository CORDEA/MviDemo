package jp.cordea.mvidemo.ui.region

import dagger.Module
import dagger.android.ContributesAndroidInjector
import jp.cordea.mvidemo.di.FragmentScope

@Module
interface RegionFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [
        RegionNavigatorModule::class
    ])
    fun contributeRegionFragment(): RegionFragment
}
