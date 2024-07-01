package com.example.studentdatafirebase

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun DeleteConfirmation(
    student: StudentRecord,
    onConfirm: (StudentRecord) -> Unit,
    onDismiss:() -> Unit
) {
    AlertDialog(
        onDismissRequest =  onDismiss ,
        confirmButton = { Button(onClick = { onConfirm(student) }) {
            Text(text = "Yes")
        }},
        text={
            Column {
                Text(
                    text = "Do you want to delete this record:",
                    fontWeight = FontWeight(800),
                    fontSize = 20.sp
                )
                Row (modifier = Modifier.fillMaxWidth()){
                    Text(
                        text = "Name: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = student.name,
                    )
                }

                Row (modifier = Modifier.fillMaxWidth()){
                    Text(
                        text = "Roll Number: ",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = student.rollNo
                    )
                }
                Row (modifier = Modifier.fillMaxWidth()){
                    Text(
                        text = "Major: ",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = student.major
                    )
                }
                Row (modifier = Modifier.fillMaxWidth()){
                    Text(
                        text = "Phone Number: ",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = student.phoneNo
                    )
                }
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}