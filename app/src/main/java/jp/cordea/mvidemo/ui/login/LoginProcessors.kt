package jp.cordea.mvidemo.ui.login

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.cordea.mvidemo.di.ActivityScope
import jp.cordea.mvidemo.mvi.MviProcessors
import javax.inject.Inject

@ActivityScope
class LoginProcessors @Inject constructor(
        private val repository: LoginRepository
) : MviProcessors<LoginAction, LoginResult> {

    private val tryLoginProcessor =
            ObservableTransformer<LoginAction.TryLoginAction, LoginResult.TryLoginResult> {
                it.flatMapMaybe {
                    repository.getApiKey()
                            .map { LoginResult.TryLoginResult.Success }
                            .cast(LoginResult.TryLoginResult::class.java)
                            .onErrorReturn { LoginResult.TryLoginResult.Failure(it) }
                            .defaultIfEmpty(LoginResult.TryLoginResult.Empty)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }
            }

    private val saveApiKeyProcessor =
            ObservableTransformer<LoginAction.SaveApiKeyAction, LoginResult.SaveApiKeyResult> {
                it.flatMapSingle {
                    repository.saveApiKey(it.apiKey)
                            .andThen(Single.just(LoginResult.SaveApiKeyResult.Success))
                            .cast(LoginResult.SaveApiKeyResult::class.java)
                            .onErrorReturn { LoginResult.SaveApiKeyResult.Failure(it) }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }
            }

    override val processor =
            ObservableTransformer<LoginAction, LoginResult> {
                it.publish {
                    Observable.merge(
                            it.ofType(LoginAction.TryLoginAction::class.java)
                                    .compose(tryLoginProcessor),
                            it.ofType(LoginAction.SaveApiKeyAction::class.java)
                                    .compose(saveApiKeyProcessor)
                    ).mergeWith(it
                            .filter {
                                it != LoginAction.TryLoginAction &&
                                        it !is LoginAction.SaveApiKeyAction
                            }
                            .flatMap {
                                Observable.error<LoginResult>(IllegalArgumentException())
                            }
                    )
                }
            }
}
