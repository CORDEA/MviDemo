package jp.cordea.mvidemo.ui.region

sealed class RegionAction {
    data class FetchRegionsAction(
            val forceFetch: Boolean
    ) : RegionAction()
}
