package com.example.studentdatafirebase

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateStudentDialog(
    student: StudentRecord,
    onDismiss: () -> Unit,
    onConfirm: (StudentRecord) -> Unit
) {
    var name by remember { mutableStateOf(student.name) }
    var rollNo by remember { mutableStateOf(student.rollNo) }
    var major by remember { mutableStateOf(student.major) }
    var phoneNo by remember { mutableStateOf(student.phoneNo) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Update Student Record") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                TextField(
                    value = rollNo,
                    onValueChange = { rollNo = it },
                    label = { Text("Roll No") }
                )
                TextField(
                    value = major,
                    onValueChange = { major = it },
                    label = { Text("Major") }
                )
                TextField(
                    value = phoneNo,
                    onValueChange = { phoneNo = it },
                    label = { Text("Phone No") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val updatedStudent = student.copy(
                        name = name,
                        rollNo = rollNo,
                        major = major,
                        phoneNo = phoneNo
                    )
                    onConfirm(updatedStudent)
                }
            ) {
                Text("Update")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
