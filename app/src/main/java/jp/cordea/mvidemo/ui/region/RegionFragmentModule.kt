package jp.cordea.mvidemo.ui.region

import dagger.Module
import dagger.android.ContributesAndroidInjector
import jp.cordea.mvidemo.di.FragmentScope
import jp.cordea.mvidemo.di.ViewModelModule

@Module
interface RegionFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [
        RegionFragmentBindsModule::class,
        RegionViewModelModule::class
    ])
    fun contributeRegionFragment(): RegionFragment
}

@Module
class RegionViewModelModule : ViewModelModule<RegionViewModel>(RegionViewModel::class)
