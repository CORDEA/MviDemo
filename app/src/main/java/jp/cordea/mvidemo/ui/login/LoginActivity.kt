package jp.cordea.mvidemo.ui.login

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import jp.cordea.mvidemo.R
import jp.cordea.mvidemo.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: LoginViewModel

    @Inject
    lateinit var navigator: LoginNavigator

    private lateinit var binding: ActivityLoginBinding

    private val disposables = CompositeDisposable()

    private val intents
        get() = Observable.merge(initialIntent, saveApiKeyIntent)

    private val initialIntent
        get() = Observable.just(LoginIntent.InitialIntent)

    private val saveApiKeyIntent
        get() = Observable.create<LoginIntent.SaveApiKey> { emitter ->
            binding.fab.setOnClickListener {
                emitter.onNext(LoginIntent.SaveApiKey(
                        binding.content?.apiKey?.text?.toString() ?: ""
                ))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setSupportActionBar(toolbar)

        bind()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    private fun bind() {
        disposables.add(viewModel.states.subscribe(::render))
        viewModel.processIntents(intents)
    }

    private fun render(state: LoginViewState) {
        if (state.isLoginSucceeded) {
            navigator.navigateToMain()
        }
        if (state.error != null) {
            navigator.showError()
        }
    }
}
