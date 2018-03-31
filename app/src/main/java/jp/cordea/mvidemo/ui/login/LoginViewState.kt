package jp.cordea.mvidemo.ui.login

data class LoginViewState(
        val error: Throwable?,
        val isLoginSucceeded: Boolean
) {
    companion object {
        fun idle(): LoginViewState =
                LoginViewState(
                        null,
                        false
                )
    }
}
