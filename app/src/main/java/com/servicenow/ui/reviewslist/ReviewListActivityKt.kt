package com.servicenow.exercise_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.servicenow.model.Review
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.servicenow.coffee.RetrofitBuilder
import com.servicenow.exercise.databinding.ActivityMainBinding
import com.servicenow.exercise.databinding.ActivityMainBinding.inflate
import com.servicenow.exercise.databinding.ReviewItemBinding
import com.servicenow.ui.reviewdetail.ReviewDetailActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ReviewListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = inflate(layoutInflater)
        val view = binding.root

        val adapter = ReviewAdapter { review ->
            startActivity(ReviewDetailActivity.makeIntent(baseContext, review))
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(binding.recyclerView.context, DividerItemDecoration.VERTICAL))
        binding.recyclerView.adapter = adapter

        val disposable = RetrofitBuilder.apiService.getReviews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { reviews ->
                    adapter.submitList(reviews.toMutableList())

                }, { error ->
                    Toast.makeText(this, "Error getting reviews", Toast.LENGTH_SHORT).show()
                }

            )

        setContentView(view)
    }
}

class ReviewAdapter(private val clickCallBack : (Review) -> Unit) : ListAdapter<Review, ReviewAdapter.ViewHolder>(ReviewDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ReviewItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position), clickCallBack)
    }

    class ViewHolder(private val binding: ReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review, clickCallBack: (Review) -> Unit) {
            binding.image.setImageResource(Review.getIconResourceFromName(review.name))
            binding.name.text = review.name
            binding.review.text = review.review
            binding.rating.text = review.rating.toFloat().toString()+" "+"⭐".repeat(review.rating)
            itemView.setOnClickListener { clickCallBack(review) }
        }
    }
}

class ReviewDiffCallback : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(
        oldItem: Review,
        newItem: Review
    ) = oldItem.name == newItem.name //Use Id if have

    override fun areContentsTheSame(
        oldItem: Review,
        newItem: Review
    ) = oldItem == newItem
}