package jp.cordea.mvidemo.mvi

import io.reactivex.Observable

interface MviViewModel<I : MviIntent, S : MviViewState> {

    val states: Observable<S>

    fun processIntents(intents: Observable<I>)
}
