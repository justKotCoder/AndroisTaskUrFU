package com.example.urfu_task.domain.usecase

class ValidateNameUseCase {
    operator fun invoke(name: String): Result<Unit> = when {
        name.isBlank() -> Result.failure(IllegalArgumentException("EMPTY"))
        name.length < 2 -> Result.failure(IllegalArgumentException("SHORT"))
        else -> Result.success(Unit)
    }
}