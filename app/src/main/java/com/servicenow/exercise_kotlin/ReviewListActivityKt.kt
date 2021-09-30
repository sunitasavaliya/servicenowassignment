package com.servicenow.exercise_kotlin


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.servicenow.coffee.CoffeeShopReviews
import com.servicenow.coffee.Review
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.servicenow.exercise.databinding.ActivityMainBinding
import com.servicenow.exercise.databinding.ActivityMainBinding.inflate
import com.servicenow.exercise.databinding.ReviewItemBinding

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

        adapter.submitList(CoffeeShopReviews.list.toMutableList()) //initial reviews data for list.

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
            binding.text1.text = review.name
            binding.text2.text = review.review

            itemView.setOnClickListener { clickCallBack(review) }
        }
    }
}

class ReviewDiffCallback : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(
        oldItem: Review,
        newItem: Review
    ) = oldItem == newItem //use .id if we have later to optimize.

    override fun areContentsTheSame(
        oldItem: Review,
        newItem: Review
    ) = oldItem == newItem
}