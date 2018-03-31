package jp.cordea.mvidemo.ui.login

import jp.cordea.mvidemo.mvi.MviIntent

sealed class LoginIntent : MviIntent {
    object InitialIntent : LoginIntent()

    data class SaveApiKey(
            val apiKey: String
    ) : LoginIntent()
}
