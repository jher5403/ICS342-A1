@file:OptIn(ExperimentalMaterial3Api::class)

package com.lab1.ics342_a1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lab1.ics342_a1.ui.theme.ICS342A1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ICS342A1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content() {
    val sheetState = rememberModalBottomSheetState()
    var showAddMenu by remember { mutableStateOf(false) }
    var taskList by remember { mutableStateOf(listOf<Task>()) }

    ICS342A1Theme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.LightGray
                    ),
                    title = {
                        Text(text = "Todo")
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        showAddMenu = true
                    },
                    modifier = Modifier,
                    contentColor = Color.Black,

                    ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add",

                        )
                }
            },
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .offset(y = 110.dp),
                contentPadding = PaddingValues(horizontal = 12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                items(taskList) { card ->
                    TaskView(card)
                }
            }
            if (showAddMenu) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showAddMenu = false
                    },
                    sheetState = sheetState
                )
                {
                    BottomSheetScaffold(
                        sheetContent = {
                        }
                    )
                    {
                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.CenterHorizontally,

                            ) {
                            var text by remember { mutableStateOf("") }
                            var errFlag by remember { mutableStateOf(false) }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp),
                                    value = text,
                                    onValueChange = { text = it },
                                    label = { Text(text = "New Task") },
                                    supportingText = {
                                        if (errFlag) {
                                            Text(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                text = "Invalid input."
                                            )
                                        }
                                    }
                                )
                                Button(
                                    modifier = Modifier
                                        .offset(x = (-20).dp, y = (-4).dp)
                                        .size(18.dp),
                                    onClick = { text = "" },
                                    contentPadding = PaddingValues(1.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = Color.Black
                                    ),
                                    border = BorderStroke(1.dp, Color.Black)
                                ) {
                                    Icon(
                                        Icons.Default.Clear,
                                        contentDescription = "Clear",
                                    )
                                }
                            }

                            // Save Button
                            Button(
                                onClick = {
                                    if (text != "") {
                                        taskList = taskList + Task(text)
                                        errFlag = false
                                        showAddMenu = false
                                    } else {
                                        errFlag = true
                                    }
                                },
                                modifier = Modifier
                                    .offset(y = 10.dp)
                                    .fillMaxWidth()
                                    .padding(10.dp),
                            ) {
                                Text(text = "Save")
                            }

                            // Cancel Button
                            OutlinedButton(
                                onClick = {
                                    showAddMenu = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                            ) {
                                Text(text = "Cancel")
                            }
                        }
                    }
                }
            }
        }
    }
}

data class Task(val taskText: String)

@Composable
fun TaskView(task: Task) {
    var checked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Cyan),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = task.taskText,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(all = 12.dp)
        )
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ICS342A1Theme {
        Content()
    }
}