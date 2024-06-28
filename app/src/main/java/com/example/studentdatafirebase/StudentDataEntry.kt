package com.example.studentdatafirebase

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding

import androidx.lifecycle.viewmodel.compose.viewModel
import java.lang.reflect.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun DataEntryForm(){
    val viewModel: StudentViewModel = viewModel()
    Column() {


        Text(
            text = "Enter Your Student Record Here"
        )
        TextField(
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
        )
        TextField(
            value = viewModel.rollNo,
            onValueChange = { viewModel.rollNo = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        TextField(
            value = viewModel.gender,
            onValueChange = { viewModel.gender = it }
        )
        TextField(
            value = viewModel.phoneNumber,
            onValueChange = { viewModel.phoneNumber = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Button(onClick = { viewModel.onSubmitForm() }) {
            Text(text = "Submit")
        }
    }

}

@Preview
@Composable
fun EnterDataPreview(){
    EnterDataPreview()
}