package com.example.studentmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.studentmanagement.ui.theme.StudentManagementTheme

data class Student(var name: String, var id: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentManagementTheme {
                StudentManagerApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentManagerApp() {
    var studentList by remember { mutableStateOf(listOf<Student>()) }
    var nameInput by remember { mutableStateOf(TextFieldValue("")) }
    var idInput by remember { mutableStateOf(TextFieldValue("")) }
    var isEditing by remember { mutableStateOf(false) }
    var selectedStudentIndex by remember { mutableStateOf(-1) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Quản lý sinh viên") })
        },
        content = { padding ->
            Column(modifier = Modifier
                .padding(padding)
                .padding(16.dp)
            ) {
                TextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text("Tên sinh viên") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = idInput,
                    onValueChange = { idInput = it },
                    label = { Text("Mã số sinh viên") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (nameInput.text.isNotBlank() && idInput.text.isNotBlank()) {
                            if (isEditing && selectedStudentIndex != -1) {
                                val updatedList = studentList.toMutableList()
                                updatedList[selectedStudentIndex] =
                                    Student(nameInput.text, idInput.text)
                                studentList = updatedList
                                isEditing = false
                                selectedStudentIndex = -1
                            } else {
                                studentList = studentList + Student(nameInput.text, idInput.text)
                            }
                            nameInput = TextFieldValue("")
                            idInput = TextFieldValue("")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isEditing) "Cập nhật sinh viên" else "Thêm sinh viên")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("Danh sách sinh viên:", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(studentList.indices.toList()) { index ->
                        val student = studentList[index]
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    nameInput = TextFieldValue(student.name)
                                    idInput = TextFieldValue(student.id)
                                    isEditing = true
                                    selectedStudentIndex = index
                                }
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Tên: ${student.name}")
                                Text("Mã số: ${student.id}")
                            }
                        }
                    }
                }
            }
        }
    )
}