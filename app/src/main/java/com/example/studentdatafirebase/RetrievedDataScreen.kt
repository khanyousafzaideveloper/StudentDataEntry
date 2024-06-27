package com.example.studentdatafirebase

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun RetrieveDataCard(){
    Card(){
        Column {
            Text(text = "Name")
            Text(text = "1")
            Text(text = "Male")
            Text(text = "1234567890")
        }
    }
}

@Preview
@Composable
fun RetreiveDataPreview(){
    RetreiveDataPreview()
}