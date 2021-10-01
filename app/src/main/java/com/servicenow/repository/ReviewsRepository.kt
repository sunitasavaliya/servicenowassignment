package com.servicenow.repository


import com.servicenow.coffee.ReviewApiService
import com.servicenow.model.Review
import io.reactivex.Single
import javax.inject.Inject

class ReviewsRepository @Inject constructor(
    private val apiService: ReviewApiService
) {
    fun getAllReviews() : Single<List<Review>> {
        return apiService.getReviews().map {
            it.map { apiReviewModel ->
                Review.fromApiModel(apiReviewModel)
            }
        }
    }
}