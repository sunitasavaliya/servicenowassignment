package com.servicenow.coffee

import com.servicenow.model.Review
import io.reactivex.Single
import retrofit2.http.GET

interface ReviewApiService {

    @GET("api/jsonBlob/893163324234285056")
    fun getReviews() : Single<List<Review>>

}