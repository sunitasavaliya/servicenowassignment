package com.servicenow.coffee

import com.servicenow.model.Review
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ReviewApiService {

    @GET("api/jsonBlob/893163324234285056")
    fun getReviews() : Single<List<Review>>

}

object RetrofitBuilder {

    private const val BASE_URL = "https://jsonblob.com/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    val apiService: ReviewApiService = getRetrofit().create(ReviewApiService::class.java)
}