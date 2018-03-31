package jp.cordea.mvidemo.ui.region

import io.reactivex.Maybe
import jp.cordea.mvidemo.api.VultrApiClient
import jp.cordea.mvidemo.api.response.Region
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegionRemoteDataSource @Inject constructor(
        private val apiClient: VultrApiClient
) : RegionDataSource {

    override fun fetchRegion(): Maybe<List<Region>> =
            apiClient.getRegions()
                    .map { it.values.toList() }
}
