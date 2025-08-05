package com.baseproject.ui.listItems

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.baseproject.data.models.Task
import com.baseproject.theme.largeSpacing
import com.baseproject.theme.onSecondaryColor
import com.baseproject.theme.smallSpacing
import com.baseproject.viewModel.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun TaskItem(
    taskViewModel: TaskViewModel = viewModel(),
    task: Task = Task(
        title = "Meeting with Client",
        description = "",
        dateTime = "Today 11:25 PM",
        isCompleted = false
    )
) {
    val scope = rememberCoroutineScope()
    var isChecked by remember { mutableStateOf(task.isCompleted) }

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(largeSpacing)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = task.title, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.onTertiaryContainer)
                Spacer(modifier = Modifier.padding(smallSpacing))
                if (task.description.isNotEmpty()){
                    Text(text = task.description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                Spacer(modifier = Modifier.padding(smallSpacing))
                Text(text = task.dateTime, style = MaterialTheme.typography.labelSmall, color = onSecondaryColor)
            }

            RadioButton(
                selected = isChecked,
                onClick = {
                    scope.launch {
                        isChecked = !isChecked
                        withContext(Dispatchers.IO){
                            taskViewModel.updateTask(task.copy(isCompleted = isChecked))
                        }
                    }
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = onSecondaryColor,
                    unselectedColor = onSecondaryColor,
                    //   checkmarkColor = primaryColorLight
                )
            )
        }
    }
}

@Preview
@Composable
fun TaskItemPreview() {
    MaterialTheme {
        TaskItem()
    }
}