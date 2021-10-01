package com.servicenow.ui.reviewdetail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.servicenow.model.Review
import com.servicenow.exercise.databinding.ActivityReviewDetailBinding

class ReviewDetailActivity : AppCompatActivity() {
    companion object {
        const val NAME_KEY = "NameKey"
        const val REVIEW_KEY = "ReviewKey"
        const val RATINGS_KEY = "RatingsKey"

        fun makeIntent(context: Context, review: Review): Intent {

            return Intent(context, ReviewDetailActivity::class.java).apply {
                this.putExtra(NAME_KEY, review.name)
                this.putExtra(REVIEW_KEY, review.review)
                this.putExtra(RATINGS_KEY, review.rating)
            }
        }
    }

    private lateinit var binding: ActivityReviewDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReviewDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val review = Review(
            name = intent.getStringExtra(NAME_KEY)
                ?: throw IllegalArgumentException("Name is required"),
            review = intent.getStringExtra(REVIEW_KEY) ?: throw IllegalArgumentException(
                "Review is required"
            ),
            rating = intent.getIntExtra(
                RATINGS_KEY,
                Int.MIN_VALUE
            )
        )

        binding.image.setImageResource(Review.getIconResourceFromName(review.name))
        binding.name.text = review.name
        binding.review.text = review.review
        binding.rating.text = review.rating.toFloat().toString()+" "+"⭐".repeat(review.rating)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}