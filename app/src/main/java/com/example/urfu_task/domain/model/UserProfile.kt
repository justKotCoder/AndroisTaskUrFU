package com.example.urfu_task.domain.model

data class UserProfile(
    val name: String,
    val age: Int,
    val gender: Gender,
    val subscribed: Boolean
)