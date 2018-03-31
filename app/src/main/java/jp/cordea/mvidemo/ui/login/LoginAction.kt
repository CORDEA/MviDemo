package jp.cordea.mvidemo.ui.login

import jp.cordea.mvidemo.mvi.MviAction

sealed class LoginAction : MviAction {
    data class SaveApiKeyAction(
            val apiKey: String
    ) : LoginAction()

    object TryLoginAction : LoginAction()
    object NoneAction : LoginAction()
}
