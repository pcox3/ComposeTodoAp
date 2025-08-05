package com.baseproject.ui.modals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.baseproject.data.models.Task
import com.baseproject.theme.extraLargeSpacing
import com.baseproject.theme.mediumSpacing
import com.baseproject.theme.onSecondaryColor
import com.baseproject.theme.onUnselectedColor
import com.baseproject.theme.selectedColor
import com.baseproject.theme.unselectedColor
import com.baseproject.todoapp.R
import com.baseproject.ui.custom.OutlineInputField
import com.baseproject.utility.convertTimeInMillisToStringDate
import com.baseproject.viewModel.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun NewTaskScreen(
    dismiss: () -> Unit = {},
    viewModel: TaskViewModel = viewModel()
) {
    var taskName by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Personal") }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "New ToDo",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = "Title Task",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
        )

        OutlineInputField(
            focusManager = focusManager,
            value = taskName,
            isError = isError,
            onValueChange = { taskName = it },
            imeAction = ImeAction.Next,
            placeholder = "Add Task Name...",
        )

        Spacer(modifier = Modifier.padding(mediumSpacing))

        CategoryInput(
            onPersonClick = { selectedCategory = "Personal" },
            onTeamsClick = { selectedCategory = "Teams" },
            selectedCategory = selectedCategory
        )

        Text(
            text = "Description (optional)",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
        )

        OutlineInputField(
            focusManager = focusManager,
            value = description,
            modifier = Modifier.height(100.dp),
            onValueChange = { description = it },
            imeAction = ImeAction.Done,
            placeholder = "Add Descriptions..",
        )

        Spacer(modifier = Modifier.padding(extraLargeSpacing))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = { dismiss() },
                modifier = Modifier.weight(1f),
            ) {
                Text("Cancel", fontSize = 16.sp)
            }
            Button(
                onClick = {
                    if (taskName.isEmpty()) {
                        isError = true
                        return@Button
                    }
                    scope.launch {
                        withContext(Dispatchers.IO){
                            viewModel.insertTask(
                                Task(title = taskName, description = description,
                                    dateTime = convertTimeInMillisToStringDate())
                            )
                        }
                    }
                    dismiss()
                },
                modifier = Modifier.weight(1f),
            ) {
                Text("Create", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun CategoryInput(
    onPersonClick: () -> Unit = {},
    onTeamsClick: () -> Unit = {}
    , selectedCategory: String = "Personal"
) {

    Text(
        text = "Category",
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.fillMaxWidth()
    )
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CategoryButton(
            text = "Personal",
            icon = Icons.Filled.Person,
            isSelected = selectedCategory == "Personal",
            onClick = { onPersonClick() },
            modifier = Modifier.weight(1f)
        )
        CategoryButton(
            text = "Teams",
            icon = ImageVector.vectorResource(R.drawable.ic_team),
            isSelected = selectedCategory == "Teams",
            onClick = { onTeamsClick() },
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
fun CategoryButton(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = buttonColors(
            containerColor = if (isSelected) Color(selectedColor.value) else unselectedColor,
            contentColor = if (isSelected) onSecondaryColor else onUnselectedColor,
        ),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Icon(imageVector = icon, contentDescription = text, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 14.sp)
    }

}

@Preview(showBackground = true)
@Composable
fun NewTaskScreenPreview() {
    NewTaskScreen()
}
