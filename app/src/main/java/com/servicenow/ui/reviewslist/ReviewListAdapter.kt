package com.servicenow.ui.reviewslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.servicenow.exercise.databinding.ReviewItemBinding
import com.servicenow.model.Review

class ReviewListAdapter(private val clickCallBack : (Review) -> Unit) : ListAdapter<Review, ReviewListAdapter.ViewHolder>(
    ReviewDiffCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ReviewItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickCallBack)
    }

    class ViewHolder(private val binding: ReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review, clickCallBack: (Review) -> Unit) {
            binding.image.setImageResource(Review.getIconResourceFromName(review.name))
            binding.name.text = review.name
            binding.rating.text = review.rating.toFloat().toString()+" "+"⭐".repeat(review.rating)
            binding.review.text = review.review

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