package jp.cordea.mvidemo.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.MembersInjector
import javax.inject.Inject

class ViewModelFactory<V : ViewModel> @Inject constructor(
        private val injector: MembersInjector<V>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            modelClass.newInstance().apply {
                injector.injectMembers(this as V)
            }
}
