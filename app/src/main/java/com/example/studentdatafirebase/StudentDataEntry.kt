package com.example.studentdatafirebase

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun DataEntryForm(){
    var name by remember { mutableStateOf("") }
    var rollNo by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    Column {


        Text(
            text = "Enter Your Student Record Here"
        )
        TextField(
            value = name,
            onValueChange = { name = it },
        )
        TextField(
            value = rollNo,
            onValueChange = { rollNo = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        TextField(
            value = gender,
            onValueChange = { gender = it }
        )
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it }
        )

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Submit")
        }
    }

}