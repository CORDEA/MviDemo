package jp.cordea.mvidemo.ui.region

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.cordea.mvidemo.di.FragmentScope
import javax.inject.Inject

@FragmentScope
class RegionProcessors @Inject constructor(
        private val repository: RegionRepository
) {

    private val fetchRegionsProcessor =
            ObservableTransformer<RegionAction.FetchRegionsAction, RegionResult.FetchRegionResult> {
                it.flatMapMaybe {
                    repository.fetchRegion(it.forceFetch)
                            .map { RegionResult.FetchRegionResult.Success(it) }
                            .cast(RegionResult.FetchRegionResult::class.java)
                            .onErrorReturn { RegionResult.FetchRegionResult.Failure(it) }
                            .defaultIfEmpty(RegionResult.FetchRegionResult.Failure(null))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }
            }

    val processor =
            ObservableTransformer<RegionAction, RegionResult> {
                it.publish {
                    it.ofType(RegionAction.FetchRegionsAction::class.java)
                            .compose(fetchRegionsProcessor)
                            .cast(RegionResult::class.java)
                            .mergeWith(it
                                    .filter {
                                        it !is RegionAction.FetchRegionsAction
                                    }
                                    .flatMap {
                                        Observable.error<RegionResult>(IllegalArgumentException())
                                    }
                            )
                }
            }
}
