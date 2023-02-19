package com.example.popcom.retrofit

import com.example.popcom.common.Constantes
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TheMovieDBClient {
    private val theMovieDBService: TheMovieDBService
    private val retrofit: Retrofit

    companion object {
        var instance: TheMovieDBClient? = null
            get() {
                if (field == null) {
                    instance = TheMovieDBClient()

                }
                return instance
            }
    }

    init {
        //Incluir el interceptor que hemos definido

        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(TheMovieDBInterceptor())

        val cliente = okHttpClientBuilder.build()

        //Construir el cliente Retrofit
        retrofit = Retrofit.Builder()
            .baseUrl(Constantes.TMDBAPI_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(cliente)
            .build()

        //Instanciamos el servicio de Retrofit a partir del objeto retrofit
        theMovieDBService = retrofit.create(TheMovieDBService::class.java)
    }

    fun getTheMovieDBService() = theMovieDBService

}