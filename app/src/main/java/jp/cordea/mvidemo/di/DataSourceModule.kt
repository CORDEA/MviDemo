package jp.cordea.mvidemo.di

import dagger.Binds
import dagger.Module
import jp.cordea.mvidemo.ui.region.RegionDataSource
import jp.cordea.mvidemo.ui.region.RegionRemoteDataSource

@Module
interface DataSourceModule {

    @Binds
    fun bindRegionDataSource(remoteDataSource: RegionRemoteDataSource): RegionDataSource
}
