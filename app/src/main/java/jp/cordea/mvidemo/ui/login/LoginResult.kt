package jp.cordea.mvidemo.ui.login

import jp.cordea.mvidemo.mvi.MviResult

sealed class LoginResult : MviResult {
    sealed class TryLoginResult : LoginResult() {
        data class Failure(
                val error: Throwable
        ) : TryLoginResult()

        object Empty : TryLoginResult()
        object Success : TryLoginResult()
    }

    sealed class SaveApiKeyResult : LoginResult() {
        data class Failure(
                val error: Throwable
        ) : SaveApiKeyResult()

        object Success : SaveApiKeyResult()
    }
}
