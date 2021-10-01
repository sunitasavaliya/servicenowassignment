package com.servicenow.exercise_kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.servicenow.exercise.databinding.ActivityReviewListBinding.inflate
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.servicenow.exercise.databinding.ActivityReviewListBinding
import com.servicenow.ui.reviewdetail.ReviewDetailActivity
import com.servicenow.ui.reviewslist.ReviewListAdapter
import com.servicenow.ui.reviewslist.ReviewListViewModel
import com.servicenow.ui.reviewslist.UILoadingState

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewListBinding

    private val viewModel by viewModels<ReviewListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = inflate(layoutInflater)
        val view = binding.root

        val adapter = ReviewListAdapter { review ->
            startActivity(ReviewDetailActivity.makeIntent(baseContext, review))
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(binding.recyclerView.context, DividerItemDecoration.VERTICAL))
        binding.recyclerView.adapter = adapter


        binding.refreshButton.setOnClickListener {
            viewModel.refresh()
        }

        viewModel.viewState.observe(this ) { viewState ->
            when (viewState.loadingState) {
                UILoadingState.initial -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.loadingView.visibility = View.GONE
                    binding.errorView.visibility = View.GONE
                }
                UILoadingState.refreshing -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.loadingView.visibility = View.VISIBLE
                    binding.errorView.visibility = View.GONE
                }
                UILoadingState.success -> {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.loadingView.visibility = View.GONE
                    binding.errorView.visibility = View.GONE
                    adapter.submitList(viewState.data?.toMutableList())
                }
                UILoadingState.error -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.loadingView.visibility = View.GONE
                    binding.errorView.visibility = View.VISIBLE
                }
            }
        }
        setContentView(view)
    }

}
