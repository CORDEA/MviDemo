package jp.cordea.mvidemo.ui.region

import jp.cordea.mvidemo.api.response.Region

data class RegionItemViewModel(
        val title: String,
        val description: String
) {

    companion object {

        fun from(region: Region) =
                RegionItemViewModel(region.name, region.continent)
    }
}
