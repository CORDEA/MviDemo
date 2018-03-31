package jp.cordea.mvidemo.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
        private val viewModels: Map<Class<*>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(model: Class<T>): T {
        val viewModel = viewModels[model] ?: viewModels
                .filter { model.isAssignableFrom(it.key) }
                .map { it.value }
                .firstOrNull()
        viewModel?.let {
            return it.get() as T
        }
        throw IllegalStateException()
    }
}
