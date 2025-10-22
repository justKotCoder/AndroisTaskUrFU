package com.example.urfu_task.presentation.profile

import com.example.urfu_task.domain.model.Gender


data class ProfileState(
    val name: String = "",
    val age: Int = 18,
    val gender: Gender = Gender.MALE,
    val subscribed: Boolean = false,
    val isSubmitting: Boolean = false,
    val nameErrorKey: String? = null, // "EMPTY" | "SHORT" | null
    val summary: String? = null
)


sealed interface ProfileEvent {
    data class NameChanged(val value: String) : ProfileEvent
    data class AgeChanged(val value: Int) : ProfileEvent
    data class GenderChanged(val value: Gender) : ProfileEvent
    data class SubscribedChanged(val value: Boolean) : ProfileEvent
    data object SubmitClicked : ProfileEvent
}