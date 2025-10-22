package com.example.urfu_task.domain.usecase

import com.example.urfu_task.domain.model.UserProfile
import com.example.urfu_task.domain.repository.UserProfileRepository


class SubmitProfileUseCase(
    private val repo: UserProfileRepository,
    private val validateName: ValidateNameUseCase
) {
    suspend operator fun invoke(profile: UserProfile): Result<UserProfile> {
        validateName(profile.name).getOrElse { return Result.failure(it) }
        if (profile.age !in 1..100) return Result.failure(IllegalArgumentException("AGE_RANGE"))
        return runCatching { repo.save(profile); profile }
    }
}