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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.baseproject.data.models.Task
import com.baseproject.theme.largeSpacing
import com.baseproject.theme.miniSpacing
import com.baseproject.theme.onSecondaryColor
import com.baseproject.utility.convertTimeInMillisToStringDate
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
    val coroutineScope = rememberCoroutineScope()
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
                Spacer(modifier = Modifier.padding(miniSpacing))
                if (task.description.isNotEmpty()){
                    Text(text = task.description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                Spacer(modifier = Modifier.padding(miniSpacing))
                if (task.updatedAt.isNullOrEmpty()){
                    Text(text = task.dateTime, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.W400), color = onSecondaryColor)
                }else{
                    Text(text = task.dateTime, style = MaterialTheme.typography.labelSmall, color = onSecondaryColor)
                }
                task.updatedAt?.let { updatedAt ->
                    Text(text = "updated: $updatedAt", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.W400), color = onSecondaryColor)
                }
            }

            RadioButton(
                selected = isChecked,
                onClick = {
                    coroutineScope.launch {
                        isChecked = !isChecked
                        withContext(Dispatchers.IO){
                            taskViewModel.updateTask(task.copy(isCompleted = isChecked,
                                updatedAt = convertTimeInMillisToStringDate()))
                        }
                    }
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = onSecondaryColor,
                    unselectedColor = onSecondaryColor
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