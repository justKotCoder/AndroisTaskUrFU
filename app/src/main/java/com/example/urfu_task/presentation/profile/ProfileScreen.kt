package com.example.urfu_task.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.urfu_task.R
import com.example.urfu_task.domain.model.Gender
import com.example.urfu_task.presentation.design.Dimens
import com.example.urfu_task.presentation.profile.components.LabeledSection

@Composable
fun ProfileRoute(viewModel: ProfileViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ProfileScreen(state = state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(state: ProfileState, onEvent: (ProfileEvent) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(Dimens.screenPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimens.sectionSpacing)
        ) {
            // Аватар (добавь файл ic_avatar_placeholder.xml в res/drawable)
            Image(
                painter = painterResource(id = R.drawable.ic_avatar_placeholder),
                contentDescription = null,
                modifier = Modifier.size(Dimens.avatarSize)
            )

            // Имя
            LabeledSection(
                title = stringResource(R.string.name_label),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = state.name,
                    onValueChange = { onEvent(ProfileEvent.NameChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.nameErrorKey != null,
                    supportingText = {
                        val err = when (state.nameErrorKey) {
                            "EMPTY" -> stringResource(R.string.name_error_empty)
                            "SHORT" -> stringResource(R.string.name_error_short)
                            else -> null
                        }
                        if (err != null) Text(err)
                    }
                )
            }

            // Возраст
            LabeledSection(
                title = stringResource(R.string.age_label),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.fillMaxWidth()) {
                    Slider(
                        value = state.age.toFloat(),
                        onValueChange = { onEvent(ProfileEvent.AgeChanged(it.toInt())) },
                        valueRange = 1f..100f,
                        steps = 99
                    )
                    Text(text = stringResource(R.string.age_value, state.age))
                }
            }

            // Пол
            LabeledSection(
                title = stringResource(R.string.gender_label),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GenderRadio(
                        selected = state.gender == Gender.MALE,
                        label = stringResource(R.string.gender_male),
                        onClick = { onEvent(ProfileEvent.GenderChanged(Gender.MALE)) }
                    )
                    GenderRadio(
                        selected = state.gender == Gender.FEMALE,
                        label = stringResource(R.string.gender_female),
                        onClick = { onEvent(ProfileEvent.GenderChanged(Gender.FEMALE)) }
                    )
                }
            }

            // Подписка
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = state.subscribed,
                    onCheckedChange = { onEvent(ProfileEvent.SubscribedChanged(it)) }
                )
                Spacer(Modifier.width(Dimens.controlSpacing))
                Text(text = stringResource(R.string.subscribe_label))
            }

            // Отправка
            Button(
                enabled = state.nameErrorKey == null && state.name.isNotBlank() && !state.isSubmitting,
                onClick = { onEvent(ProfileEvent.SubmitClicked) },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.isSubmitting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(text = stringResource(R.string.send))
                }
            }

            // Сводка (покажем, если заполнена после отправки)
            state.summary?.let { summary ->
                ElevatedCard(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = stringResource(R.string.summary_title),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(text = summary)
                    }
                }
            }
        }
    }
}

@Composable
private fun GenderRadio(
    selected: Boolean,
    label: String,
    onClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = onClick)
        Spacer(Modifier.width(8.dp))
        Text(text = label)
    }
}
