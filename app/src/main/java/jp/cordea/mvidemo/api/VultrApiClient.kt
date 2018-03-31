package jp.cordea.mvidemo.api

import io.reactivex.Maybe
import jp.cordea.mvidemo.BuildConfig
import jp.cordea.mvidemo.KeyManager
import jp.cordea.mvidemo.api.response.Region
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VultrApiClient @Inject constructor(
        private val manager: KeyManager,
        private val retrofitBuilder: Retrofit.Builder
) : VultrApi {

    private val service: Maybe<VultrApi>
        get() {
            val key = manager.get() ?: return Maybe.empty()
            var builder = OkHttpClient.Builder()
                    .addInterceptor {
                        it.proceed(it.request()
                                .newBuilder()
                                .addHeader("API-Key", key)
                                .build())
                    }

            if (BuildConfig.DEBUG) {
                builder = builder
                        .addInterceptor(
                                HttpLoggingInterceptor()
                                        .setLevel(HttpLoggingInterceptor.Level.BASIC)
                        )
            }

            return Maybe.just(retrofitBuilder
                    .client(builder.build())
                    .build()
                    .create(VultrApi::class.java))
        }

    override fun getRegions(): Maybe<Map<String, Region>> =
            service.flatMap { it.getRegions() }
}
