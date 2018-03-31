package jp.cordea.mvidemo.ui.region

import jp.cordea.mvidemo.mvi.MviIntent

sealed class RegionIntent : MviIntent {
    object InitialIntent : RegionIntent()
}
