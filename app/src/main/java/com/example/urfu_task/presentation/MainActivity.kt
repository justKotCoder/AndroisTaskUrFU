package com.example.urfu_task.presentation



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.urfu_task.di.ServiceLocator
import com.example.urfu_task.presentation.design.AppTheme
import com.example.urfu_task.presentation.profile.ProfileRoute
import com.example.urfu_task.presentation.profile.ProfileViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return ProfileViewModel(
                            validateName = ServiceLocator.validateName,
                            submit = ServiceLocator.submitProfile
                        ) as T
                    }
                }
                val vm: ProfileViewModel = viewModel(factory = factory)
                ProfileRoute(vm)
            }
        }
    }
}