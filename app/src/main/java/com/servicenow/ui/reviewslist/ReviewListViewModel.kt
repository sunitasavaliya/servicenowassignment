package com.servicenow.ui.reviewslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.servicenow.model.Review
import com.servicenow.repository.ReviewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.Disposable
import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.schedulers.Schedulers


@HiltViewModel
class ReviewListViewModel @Inject constructor(
    private val repository: ReviewsRepository
) : ViewModel() {

    var viewState : MutableLiveData<ReviewListViewState> = MutableLiveData()

    private var disposables: MutableList<Disposable> = mutableListOf()
    init {
        refresh()
    }

    fun refresh() {
        viewState.postValue(ReviewListViewState(UILoadingState.refreshing, null))
        disposables.plusAssign(repository.getAllReviews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { reviews ->
                    viewState.postValue(ReviewListViewState(UILoadingState.success, reviews))

                }, { error ->
                    viewState.postValue(ReviewListViewState(UILoadingState.error, null))
                }

            ))
    }


    override fun onCleared() {
        super.onCleared()
        disposables.forEach {
            it.dispose()
        }
        disposables.clear()
    }

}

data class ReviewListViewState(
    val loadingState: UILoadingState = UILoadingState.initial,
    val data: List<Review>? = null
)

//Move to a shared library
//Should idealy be a sealed class <T>
enum class UILoadingState {
    initial, refreshing, success, error
}