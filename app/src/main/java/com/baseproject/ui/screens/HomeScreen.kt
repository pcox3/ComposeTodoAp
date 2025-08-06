package com.baseproject.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.baseproject.theme.extraLargeRadius
import com.baseproject.theme.largeSpacing
import com.baseproject.theme.mediumSpacing
import com.baseproject.theme.smallSpacing
import com.baseproject.todoapp.R
import com.baseproject.ui.listItems.TaskItem
import com.baseproject.ui.modals.NewTaskScreen
import com.baseproject.viewModel.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(taskViewModel: TaskViewModel = viewModel()){
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val tasks by taskViewModel.getAllTasks().observeAsState(initial = emptyList())

    Box(modifier = Modifier
        .padding(largeSpacing)
        .fillMaxWidth()
        .fillMaxHeight()) {

        Column {

            AnimatedVisibility(
                visible = !showSheet,
                enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                exit = fadeOut(animationSpec = tween(durationMillis = 500))
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                taskViewModel.deleteAllTasks()
                            }
                        }
                    }
                ) {
                    Text("Clear All")
                }
            }

            Spacer(modifier = Modifier.padding(smallSpacing))

            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(mediumSpacing)
            ) {
                items(tasks) { task ->
                    if (tasks.isEmpty()){
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("No tasks to show")
                        }
                    }else{
                        TaskItem(
                            taskViewModel, task)
                    }
                }
            }


        }

        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = {
                showSheet = true
                coroutineScope.launch { sheetState.show() }
            }, shape = RoundedCornerShape(extraLargeRadius)
        ) {
            Image(painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add Task")
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    showSheet = false
                    sheetState.hide()
                }
            },
            sheetState = sheetState
        ) {
            NewTaskScreen(
                dismiss = {
                    coroutineScope.launch {
                        sheetState.hide()
                        showSheet = false
                    }
                }, viewModel = taskViewModel
            )
        }
    }
}



@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}
