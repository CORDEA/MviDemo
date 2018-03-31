package jp.cordea.mvidemo.ui.login

sealed class LoginAction {
    data class SaveApiKeyAction(
            val apiKey: String
    ) : LoginAction()

    object TryLoginAction : LoginAction()
    object NoneAction : LoginAction()
}
