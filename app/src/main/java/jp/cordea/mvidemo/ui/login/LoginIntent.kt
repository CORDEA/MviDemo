package jp.cordea.mvidemo.ui.login

sealed class LoginIntent {
    object InitialIntent : LoginIntent()

    data class SaveApiKey(
            val apiKey: String
    ) : LoginIntent()
}
