package com.aleesha.domain.aero.data.remote

import com.aleesha.domain.aero.data.model.AirlinesItem
import retrofit2.http.GET

interface AirlinesApi {

    @GET("h/mobileapis/directory/airlines")
    suspend fun getAirlines(): List<AirlinesItem>

    companion object {
        internal const val BASE_URL = "https://www.abcxyzdomain.com/"
    }
}
