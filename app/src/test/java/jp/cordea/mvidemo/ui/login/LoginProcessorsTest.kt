package jp.cordea.mvidemo.ui.login

import com.google.common.truth.Truth
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LoginProcessorsTest {

    @Mock
    private lateinit var repository: LoginRepository

    @InjectMocks
    private lateinit var processors: LoginProcessors

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        RxJavaPlugins.setIoSchedulerHandler {
            Schedulers.trampoline()
        }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            Schedulers.trampoline()
        }
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun tryLogin() {
        whenever(repository.getApiKey()).thenReturn(
                Maybe.just("api")
        )
        var test = Observable
                .just(LoginAction.TryLoginAction)
                .compose(processors.processor)
                .test()
        test.awaitTerminalEvent()
        test.assertComplete()
        test.assertNoErrors()
        test.assertValueCount(1)

        test.values()[0].let {
            Truth.assertThat(it).isInstanceOf(LoginResult.TryLoginResult.Success::class.java)
        }

        whenever(repository.getApiKey()).thenReturn(
                Maybe.empty()
        )
        test = Observable
                .just(LoginAction.TryLoginAction)
                .compose(processors.processor)
                .test()
        test.awaitTerminalEvent()
        test.assertComplete()
        test.assertNoErrors()
        test.assertValueCount(1)

        test.values()[0].let {
            Truth.assertThat(it).isInstanceOf(LoginResult.TryLoginResult.Empty::class.java)
        }

        whenever(repository.getApiKey()).thenReturn(
                Maybe.error(IllegalStateException())
        )
        test = Observable
                .just(LoginAction.TryLoginAction)
                .compose(processors.processor)
                .test()
        test.awaitTerminalEvent()
        test.assertComplete()
        test.assertNoErrors()
        test.assertValueCount(1)

        (test.values()[0] as LoginResult.TryLoginResult.Failure).let {
            Truth.assertThat(it.error).isInstanceOf(IllegalStateException::class.java)
        }
    }

    @Test
    fun saveApiKey() {
        whenever(repository.saveApiKey(Mockito.anyString())).thenReturn(
                Completable.complete()
        )
        var test = Observable
                .just(LoginAction.SaveApiKeyAction("api"))
                .compose(processors.processor)
                .test()
        test.awaitTerminalEvent()
        test.assertComplete()
        test.assertNoErrors()
        test.assertValueCount(1)

        test.values()[0].let {
            Truth.assertThat(it).isInstanceOf(LoginResult.SaveApiKeyResult.Success::class.java)
        }

        whenever(repository.saveApiKey(Mockito.anyString())).thenReturn(
                Completable.error(IllegalStateException())
        )
        test = Observable
                .just(LoginAction.SaveApiKeyAction("api"))
                .compose(processors.processor)
                .test()
        test.awaitTerminalEvent()
        test.assertComplete()
        test.assertNoErrors()
        test.assertValueCount(1)

        (test.values()[0] as LoginResult.SaveApiKeyResult.Failure).let {
            Truth.assertThat(it.error).isInstanceOf(IllegalStateException::class.java)
        }
    }

    @Test
    fun others() {
        val test = Observable
                .just(LoginAction.NoneAction)
                .compose(processors.processor)
                .test()

        test.awaitTerminalEvent()
        test.assertError(IllegalArgumentException::class.java)
    }
}
