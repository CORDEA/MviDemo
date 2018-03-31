package jp.cordea.mvidemo.ui.region

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import jp.cordea.mvidemo.api.response.Region
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegionRepository @Inject constructor(
        private val dataSource: RegionDataSource,
        private val localDataSource: RegionLocalDataSource
) {

    private val regions = PublishProcessor.create<List<Region>>()

    fun getRegions(): Flowable<List<Region>> =
            regions

    fun fetchRegion(forceFetch: Boolean): Maybe<List<Region>> =
            if (forceFetch) {
                dataSource.fetchRegion()
            } else {
                localDataSource.fetchRegion()
                        .switchIfEmpty(
                                dataSource.fetchRegion()
                                        .doOnSuccess { localDataSource.cacheRegion(it) }
                        )
            }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess { regions.onNext(it) }
                    .doOnError { regions.onError(it) }

}
