package jp.cordea.mvidemo.ui.login

import io.reactivex.Completable
import io.reactivex.Maybe
import jp.cordea.mvidemo.KeyManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
        private val keyManager: KeyManager
) {

    fun getApiKey(): Maybe<String> {
        val key = keyManager.get() ?: return Maybe.empty<String>()
        return Maybe.just(key)
    }

    fun saveApiKey(apiKey: String): Completable =
            Completable.create {
                keyManager.set(apiKey)
                it.onComplete()
            }
}
