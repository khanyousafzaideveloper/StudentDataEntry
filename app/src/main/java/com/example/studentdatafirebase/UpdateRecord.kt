package com.example.studentdatafirebase

import androidx.compose.runtime.Composable

@Composable
fun UpdateStudentDialog(
    student: StudentRecord,
    onDismiss: () -> Unit,
    onConfirm: (StudentRecord) -> Unit
) {
    var name by remember { mutableStateOf(student.name) }
    var rollNo by remember { mutableStateOf(student.rollNo) }
    var gender by remember { mutableStateOf(student.gender) }
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
                    value = gender,
                    onValueChange = { gender = it },
                    label = { Text("Gender") }
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
                        gender = gender,
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
