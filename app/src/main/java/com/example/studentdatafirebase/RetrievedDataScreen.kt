package com.example.studentdatafirebase

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.civicengagementplatform.ui.ErrorScreen
import com.example.civicengagementplatform.ui.LoadingScreen


@Composable
fun RetrieveDataCard(){

    val viewModel: StudentViewModel = viewModel()
    val studentRecordUiState by viewModel.studentRecordUiState

    when (studentRecordUiState) {
        is StudentRecordUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        is StudentRecordUiState.Success -> {
            val studentRecordList = (studentRecordUiState as StudentRecordUiState.Success).studentRecordList
            StudentList(students = studentRecordList)
        }
        is StudentRecordUiState.Error -> ErrorScreen(modifier = Modifier.fillMaxSize())
    }
}


@Composable
fun StudentList(students: List<StudentRecord>){


    LazyColumn() {
        items(students.size) { index ->
            StudentCard(
             record = students[index]
            )
        }
    }

}

@Composable
fun StudentCard(record: StudentRecord){
    Card(modifier = Modifier.fillMaxWidth() .padding(8.dp)){
        Column {
            Text(text = "Name: ${record.name}")
            Text(text = "Roll Number: ${record.rollNo}")
            Text(text = "Gender: ${record.gender}")
            Text(text = " Phone Number: ${record.phoneNo}")
        }
    }
}
@Preview
@Composable
fun RetreiveDataPreview(){
    RetreiveDataPreview()
}