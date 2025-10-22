package com.example.urfu_task.di

import com.example.urfu_task.data.repository.InMemoryUserProfileRepository
import com.example.urfu_task.domain.repository.UserProfileRepository
import com.example.urfu_task.domain.usecase.SubmitProfileUseCase
import com.example.urfu_task.domain.usecase.ValidateNameUseCase

object ServiceLocator {
    // Data
    val userRepo: UserProfileRepository by lazy { InMemoryUserProfileRepository() }


    // Use cases
    val validateName by lazy { ValidateNameUseCase() }
    val submitProfile by lazy { SubmitProfileUseCase(userRepo, validateName) }
}