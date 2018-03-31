package jp.cordea.mvidemo.ui.main

import dagger.Module
import dagger.android.ContributesAndroidInjector
import jp.cordea.mvidemo.di.ActivityScope
import jp.cordea.mvidemo.ui.region.RegionFragmentModule

@Module
interface MainActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        RegionFragmentModule::class
    ])
    fun contributeMainActivity(): MainActivity
}
