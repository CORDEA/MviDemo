package jp.cordea.mvidemo.ui.region

import jp.cordea.mvidemo.api.response.Region
import jp.cordea.mvidemo.mvi.MviResult

sealed class RegionResult : MviResult {
    sealed class FetchRegionResult : RegionResult() {
        data class Success(
                val regions: List<Region>
        ) : FetchRegionResult()

        data class Failure(
                val error: Throwable?
        ) : FetchRegionResult()
    }
}
