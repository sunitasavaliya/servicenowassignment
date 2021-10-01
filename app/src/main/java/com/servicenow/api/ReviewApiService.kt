package com.servicenow.coffee

import com.servicenow.api.ReviewPojo
import io.reactivex.Single
import retrofit2.http.GET

interface ReviewApiService {

    @GET("api/jsonBlob/893163324234285056")
    fun getReviews() : Single<List<ReviewPojo>>

}