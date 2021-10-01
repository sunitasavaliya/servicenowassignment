package com.servicenow.di

import com.servicenow.coffee.ReviewApiService
import com.servicenow.repository.ReviewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideReviewsRepository(
        reviewService: ReviewApiService
    ): ReviewsRepository{
        return ReviewsRepository(
            apiService = reviewService
        )
    }
}