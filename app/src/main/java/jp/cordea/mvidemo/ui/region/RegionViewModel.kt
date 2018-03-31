package jp.cordea.mvidemo.ui.region

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import jp.cordea.mvidemo.api.response.Region
import jp.cordea.mvidemo.di.FragmentScope
import javax.inject.Inject

@FragmentScope
class RegionViewModel @Inject constructor(
        private val repository: RegionRepository
) {

    fun regions(): LiveData<List<Region>> =
            LiveDataReactiveStreams.fromPublisher(repository.getRegions())
}
