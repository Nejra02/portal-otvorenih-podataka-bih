package com.example.projekatzavrsni.data.api.newborns

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Newborns {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("api/NewbornByBirthDate/List")
    suspend fun getNewborns(@Body request: NewbornRequest): NewbornApiResponse
}
