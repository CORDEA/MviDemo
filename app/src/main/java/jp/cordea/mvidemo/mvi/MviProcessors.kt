package jp.cordea.mvidemo.mvi

import io.reactivex.ObservableTransformer

interface MviProcessors<A : MviAction, R : MviResult> {

    val processor: ObservableTransformer<A, R>
}
