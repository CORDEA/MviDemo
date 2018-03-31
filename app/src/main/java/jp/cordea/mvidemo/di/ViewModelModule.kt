package jp.cordea.mvidemo.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import dagger.Module
import dagger.Provides
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule<T : ViewModel>(
        private val kClass: KClass<T>
) {

    @Provides
    fun provideViewModel(
            activity: FragmentActivity,
            factory: ViewModelFactory<T>
    ): T =
            ViewModelProviders.of(activity, factory).get(kClass.java)
}
