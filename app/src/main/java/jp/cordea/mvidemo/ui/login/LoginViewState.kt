package jp.cordea.mvidemo.ui.login

import jp.cordea.mvidemo.mvi.MviViewState

data class LoginViewState(
        val error: Throwable?,
        val isLoginSucceeded: Boolean
) : MviViewState {
    companion object {
        fun idle(): LoginViewState =
                LoginViewState(
                        null,
                        false
                )
    }
}
