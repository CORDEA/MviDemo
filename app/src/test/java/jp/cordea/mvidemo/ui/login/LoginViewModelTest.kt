package jp.cordea.mvidemo.ui.login

import com.google.common.truth.Truth
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoginViewModelTest {

    @Mock
    private lateinit var processors: LoginProcessors

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun initialIntent() {
        var viewModel = LoginViewModel().also { it.processors = processors }
        whenever(processors.processor).thenReturn(
                ObservableTransformer {
                    it.map { LoginResult.TryLoginResult.Success }
                }
        )
        viewModel.states.test().let {
            viewModel.processIntents(Observable.empty())
            it.awaitTerminalEvent()
            it.assertComplete()
            it.assertNoErrors()
            it.assertValueCount(1)
            // initial status.
            it.values()[0].let {
                Truth.assertThat(it.error).isNull()
                Truth.assertThat(it.isLoginSucceeded).isFalse()
            }
        }

        viewModel = LoginViewModel().also { it.processors = processors }
        whenever(processors.processor).thenReturn(
                ObservableTransformer {
                    it.map { LoginResult.TryLoginResult.Success }
                }
        )
        viewModel.states.test().let {
            viewModel.processIntents(Observable.just(LoginIntent.InitialIntent))
            it.awaitTerminalEvent()
            it.assertComplete()
            it.assertNoErrors()
            // initial status + successful status.
            it.assertValueCount(2)

            it.values()[1].let {
                Truth.assertThat(it.error).isNull()
                Truth.assertThat(it.isLoginSucceeded).isTrue()
            }
        }

        viewModel = LoginViewModel().also { it.processors = processors }
        whenever(processors.processor).thenReturn(
                ObservableTransformer {
                    it.map { LoginResult.TryLoginResult.Empty }
                }
        )
        viewModel.states.test().let {
            viewModel.processIntents(Observable.just(LoginIntent.InitialIntent))
            it.awaitTerminalEvent()
            it.assertComplete()
            it.assertNoErrors()
            // receive only initial status
            // because there is no difference between second state and initial state.
            it.assertValueCount(1)

            it.values()[0].let {
                Truth.assertThat(it.error).isNull()
                Truth.assertThat(it.isLoginSucceeded).isFalse()
            }
        }

        viewModel = LoginViewModel().also { it.processors = processors }
        whenever(processors.processor).thenReturn(
                ObservableTransformer {
                    it.map { LoginResult.TryLoginResult.Failure(IllegalStateException()) }
                }
        )
        viewModel.states.test().let {
            viewModel.processIntents(Observable.just(LoginIntent.InitialIntent))
            it.awaitTerminalEvent()
            it.assertComplete()
            it.assertNoErrors()
            it.assertValueCount(2)

            it.values()[1].let {
                Truth.assertThat(it.error).isInstanceOf(IllegalStateException::class.java)
                Truth.assertThat(it.isLoginSucceeded).isFalse()
            }
        }
    }

    @Test
    fun saveApiKey() {
        var viewModel = LoginViewModel().also { it.processors = processors }
        whenever(processors.processor).thenReturn(
                ObservableTransformer {
                    it.map { LoginResult.SaveApiKeyResult.Success }
                }
        )
        viewModel.states.test().let {
            viewModel.processIntents(Observable.empty())
            it.awaitTerminalEvent()
            it.assertComplete()
            it.assertNoErrors()
            it.assertValueCount(1)

            it.values()[0].let {
                Truth.assertThat(it.error).isNull()
                Truth.assertThat(it.isLoginSucceeded).isFalse()
            }
        }

        viewModel = LoginViewModel().also { it.processors = processors }
        whenever(processors.processor).thenReturn(
                ObservableTransformer {
                    it.map { LoginResult.SaveApiKeyResult.Success }
                }
        )
        viewModel.states.test().let {
            viewModel.processIntents(Observable.just(LoginIntent.SaveApiKey("api")))
            it.awaitTerminalEvent()
            it.assertComplete()
            it.assertNoErrors()
            it.assertValueCount(2)

            it.values()[1].let {
                Truth.assertThat(it.error).isNull()
                Truth.assertThat(it.isLoginSucceeded).isTrue()
            }
        }

        viewModel = LoginViewModel().also { it.processors = processors }
        whenever(processors.processor).thenReturn(
                ObservableTransformer {
                    it.map { LoginResult.SaveApiKeyResult.Success }
                }
        )
        viewModel.states.test().let {
            viewModel.processIntents(Observable.just(LoginIntent.SaveApiKey("")))
            it.awaitTerminalEvent()
            it.assertComplete()
            it.assertNoErrors()
            it.assertValueCount(1)

            it.values()[0].let {
                Truth.assertThat(it.error).isNull()
                Truth.assertThat(it.isLoginSucceeded).isFalse()
            }
        }

        viewModel = LoginViewModel().also { it.processors = processors }
        whenever(processors.processor).thenReturn(
                ObservableTransformer {
                    it.map { LoginResult.SaveApiKeyResult.Failure(IllegalStateException()) }
                }
        )
        viewModel.states.test().let {
            viewModel.processIntents(Observable.just(LoginIntent.SaveApiKey("api")))
            it.awaitTerminalEvent()
            it.assertComplete()
            it.assertNoErrors()
            it.assertValueCount(2)

            it.values()[1].let {
                Truth.assertThat(it.error).isInstanceOf(IllegalStateException::class.java)
                Truth.assertThat(it.isLoginSucceeded).isFalse()
            }
        }
    }
}
