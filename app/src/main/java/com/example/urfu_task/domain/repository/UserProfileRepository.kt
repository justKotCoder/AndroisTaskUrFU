package com.example.urfu_task.domain.repository

import com.example.urfu_task.domain.model.UserProfile


interface UserProfileRepository {
    suspend fun save(profile: UserProfile)
    suspend fun last(): UserProfile?
}