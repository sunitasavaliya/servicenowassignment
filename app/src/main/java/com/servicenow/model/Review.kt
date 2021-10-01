package com.servicenow.model

import com.servicenow.api.ReviewPojo
import com.servicenow.exercise.R

data class Review(
    var name: String,
    var review: String,
    var rating: Int,
) {

    override fun toString(): String {
        return name
    }

    companion object {
        @JvmStatic
        fun getIconResourceFromName(name: String): Int {
            when (name) {
                "Lofty" -> return R.drawable.bean_bag
                "Zumbar" -> return R.drawable.coffee_cup
                "Blue Bottle" -> return R.drawable.coffee_grinder
                "Bird Rock" -> return R.drawable.coffee_maker
                "Better Buzz Coffee" -> return R.drawable.coffee_shop
            }
            return -1
        }
        //Mapper function from network model to domain model
        fun fromApiModel(apiReview: ReviewPojo) : Review {
            return Review(
                apiReview.name, apiReview.review, apiReview.rating
            )
        }
    }
}