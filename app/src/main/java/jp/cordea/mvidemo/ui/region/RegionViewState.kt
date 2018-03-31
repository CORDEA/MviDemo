package jp.cordea.mvidemo.ui.region

import jp.cordea.mvidemo.api.response.Region
import jp.cordea.mvidemo.mvi.MviViewState

data class RegionViewState(
        val regions: List<Region>,
        val error: Throwable?
) : MviViewState {
    companion object {
        fun idle(): RegionViewState =
                RegionViewState(
                        emptyList(),
                        null
                )
    }
}
