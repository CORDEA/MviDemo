package jp.cordea.mvidemo.ui.region

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import jp.cordea.mvidemo.mvi.MviViewModel
import javax.inject.Inject

class RegionViewModel : ViewModel(), MviViewModel<RegionIntent, RegionViewState> {

    @Inject
    lateinit var processors: RegionProcessors

    private val intentsSubject: PublishSubject<RegionIntent> = PublishSubject.create()

    private val intentFilter = ObservableTransformer<RegionIntent, RegionIntent> {
        it.publish {
            Observable.merge(
                    it.ofType(RegionIntent.InitialIntent::class.java).take(1),
                    it.filter { it != RegionIntent.InitialIntent }
            )
        }
    }

    private val reducer = BiFunction { previous: RegionViewState, result: RegionResult ->
        when (result) {
            is RegionResult.FetchRegionResult.Success ->
                previous.copy(regions = result.regions)
            is RegionResult.FetchRegionResult.Failure ->
                previous.copy(error = result.error)
        }
    }

    override val states by lazy {
        intentsSubject
                .compose(intentFilter)
                .map(this::actionFromIntent)
                .compose(processors.processor)
                .scan(RegionViewState.idle(), reducer)
                .distinctUntilChanged()
                .replay(1)
                .autoConnect(0)
    }

    override fun processIntents(intents: Observable<RegionIntent>) =
            intents.subscribe(intentsSubject)

    private fun actionFromIntent(intent: RegionIntent): RegionAction =
            when (intent) {
                RegionIntent.InitialIntent ->
                    RegionAction.FetchRegionsAction(true)
            }
}
