package jp.cordea.mvidemo.ui.region

import io.reactivex.Maybe
import jp.cordea.mvidemo.api.response.Region

interface RegionDataSource {

    fun fetchRegion(): Maybe<List<Region>>
}
