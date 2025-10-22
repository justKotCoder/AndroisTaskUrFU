package com.example.urfu_task.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urfu_task.domain.model.UserProfile
import com.example.urfu_task.domain.usecase.SubmitProfileUseCase
import com.example.urfu_task.domain.usecase.ValidateNameUseCase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val validateName: ValidateNameUseCase,
    private val submit: SubmitProfileUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()


    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.NameChanged -> {
                _state.value = _state.value.copy(
                    name = event.value,
                    nameErrorKey = validateName(event.value).exceptionOrNull()?.message
                )
            }
            is ProfileEvent.AgeChanged -> _state.value = _state.value.copy(age = event.value)
            is ProfileEvent.GenderChanged -> _state.value = _state.value.copy(gender = event.value)
            is ProfileEvent.SubscribedChanged -> _state.value = _state.value.copy(subscribed = event.value)
            ProfileEvent.SubmitClicked -> submit()
        }
    }


    private fun submit() = viewModelScope.launch {
        val s = _state.value
        _state.value = s.copy(isSubmitting = true, summary = null)
        val profile = UserProfile(s.name.trim(), s.age, s.gender, s.subscribed)
        val result = submit(profile)
        _state.value = result.fold(
            onSuccess = {
                s.copy(
                    isSubmitting = false,
                    summary = "${it.name}, ${it.age}, ${it.gender.name}, subscribed=${it.subscribed}"
                )
            },
            onFailure = { err ->
                s.copy(isSubmitting = false, nameErrorKey = err.message)
            }
        )
    }
}