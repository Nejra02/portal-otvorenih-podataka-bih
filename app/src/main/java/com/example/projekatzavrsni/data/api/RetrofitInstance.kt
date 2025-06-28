package com.example.projekatzavrsni.data.api

import com.example.projekatzavrsni.data.api.newborns.Newborns
import com.example.projekatzavrsni.data.api.persons.Persons
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://odp.iddeea.gov.ba:8096/"
    private const val TOKEN = "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIyMDMxIiwibmJmIjoxNzUxMTMxNTY1LCJleHAiOjE3NTEyMTc5NjUsImlhdCI6MTc1MTEzMTU2NX0.C9GA2F1lJaGWy5n9iKgYQ7WERFMRTnCz5rTflHd2AVxwdF7tZGWKYGSNhyP1httZgR2KeC-U4tY5Rkl-n2WSDg"

    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", TOKEN)
            .build()
        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: Newborns by lazy {
        retrofit.create(Newborns::class.java)
    }

    val personsApi: Persons by lazy {
        retrofit.create(Persons::class.java)
    }

}
