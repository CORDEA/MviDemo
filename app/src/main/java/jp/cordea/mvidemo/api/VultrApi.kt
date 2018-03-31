package jp.cordea.mvidemo.api

import io.reactivex.Maybe
import jp.cordea.mvidemo.api.response.Region
import retrofit2.http.GET

interface VultrApi {

    @GET("v1/regions/list")
    fun getRegions(): Maybe<Map<String, Region>>
}
