package jp.cordea.mvidemo.ui.login

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import jp.cordea.mvidemo.mvi.MviViewModel
import javax.inject.Inject

class LoginViewModel : ViewModel(), MviViewModel<LoginIntent, LoginViewState> {

    @Inject
    lateinit var processors: LoginProcessors

    private val intentsSubject: PublishSubject<LoginIntent> = PublishSubject.create()

    private val intentFilter = ObservableTransformer<LoginIntent, LoginIntent> {
        it.publish {
            Observable.merge(
                    it.ofType(LoginIntent.InitialIntent::class.java).take(1),
                    it.ofType(LoginIntent.SaveApiKey::class.java)
            )
        }
    }

    private val reducer = BiFunction { previous: LoginViewState, result: LoginResult ->
        when (result) {
            is LoginResult.TryLoginResult -> when (result) {
                LoginResult.TryLoginResult.Success ->
                    previous.copy(isLoginSucceeded = true)
                LoginResult.TryLoginResult.Empty ->
                    previous.copy(error = null)
                is LoginResult.TryLoginResult.Failure ->
                    previous.copy(error = result.error)
            }
            is LoginResult.SaveApiKeyResult -> when (result) {
                LoginResult.SaveApiKeyResult.Success ->
                    previous.copy(isLoginSucceeded = true)
                is LoginResult.SaveApiKeyResult.Failure ->
                    previous.copy(error = result.error)
            }
        }
    }

    override val states by lazy {
        intentsSubject
                .compose(intentFilter)
                .map(this::actionFromIntent)
                .filter { it != LoginAction.NoneAction }
                .compose(processors.processor)
                .scan(LoginViewState.idle(), reducer)
                .distinctUntilChanged()
                .replay(1)
                .autoConnect(0)
    }

    override fun processIntents(intents: Observable<LoginIntent>) =
            intents.subscribe(intentsSubject)

    private fun actionFromIntent(intent: LoginIntent): LoginAction =
            when (intent) {
                LoginIntent.InitialIntent ->
                    LoginAction.TryLoginAction
                is LoginIntent.SaveApiKey ->
                    if (intent.apiKey.isBlank()) {
                        LoginAction.NoneAction
                    } else {
                        LoginAction.SaveApiKeyAction(intent.apiKey)
                    }
            }
}
