package com.example.urfu_task.data.repository

import com.example.urfu_task.domain.model.UserProfile
import com.example.urfu_task.domain.repository.UserProfileRepository


class InMemoryUserProfileRepository : UserProfileRepository {
    private var cached: UserProfile? = null
    override suspend fun save(profile: UserProfile) { cached = profile }
    override suspend fun last(): UserProfile? = cached
}
