package jp.cordea.mvidemo.ui.region

import jp.cordea.mvidemo.api.response.Region

data class RegionViewState(
        val regions: List<Region>,
        val error: Throwable?
) {
    companion object {
        fun idle(): RegionViewState =
                RegionViewState(
                        emptyList(),
                        null
                )
    }
}
