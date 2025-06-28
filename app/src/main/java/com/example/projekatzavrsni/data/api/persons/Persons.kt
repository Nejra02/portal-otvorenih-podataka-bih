package com.example.projekatzavrsni.data.api.persons

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Persons {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("api/PersonsByRecordDate/list")
    suspend fun getPersons(@Body request: PersonsRequest): PersonsApiResponse
}
