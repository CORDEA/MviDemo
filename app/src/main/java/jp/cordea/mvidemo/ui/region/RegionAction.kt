package jp.cordea.mvidemo.ui.region

import jp.cordea.mvidemo.mvi.MviAction

sealed class RegionAction : MviAction {
    data class FetchRegionsAction(
            val forceFetch: Boolean
    ) : RegionAction()
}
