package com.example.urfu_task.presentation.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.urfu_task.presentation.design.Dimens


@Composable
fun LabeledSection(title: String, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Column(modifier) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(Dimens.controlSpacing))
        content()
    }
}