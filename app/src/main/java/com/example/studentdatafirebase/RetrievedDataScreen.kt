package com.example.studentdatafirebase

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.civicengagementplatform.ui.ErrorScreen
import com.example.civicengagementplatform.ui.LoadingScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun RetrieveDataCard(){

    val showNotificationDialog = remember { mutableStateOf(false) }

    // Android 13 Api 33 - runtime notification permission has been added
    val notificationPermissionState = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )
    if (showNotificationDialog.value) FirebaseMessagingNotificationPermissionDialog(
        showNotificationDialog = showNotificationDialog,
        notificationPermissionState = notificationPermissionState
    )

    LaunchedEffect(key1=Unit){
        if (notificationPermissionState.status.isGranted ||
            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
        ) {
            Firebase.messaging.subscribeToTopic("Tutorial")
        } else showNotificationDialog.value = true
    }


    val studentViewModel: StudentViewModel = viewModel()
    val studentRecordUiState by studentViewModel.studentRecordUiState

    if (studentViewModel.showUpdateDialog) {
        UpdateStudentDialog(
            student = studentViewModel.studentToUpdate,
            onDismiss = { studentViewModel.showUpdateDialog = false },
            onConfirm = { updatedStudent ->
                studentViewModel.studentToUpdate = updatedStudent
                studentViewModel.updateStudentRecord()
            }
        )
    }
    
    if(studentViewModel.showDeleteDialog){
        DeleteConfirmation(
            student = studentViewModel.studentToDelete,
            onConfirm =  {deleteStudent ->
                studentViewModel.studentToDelete = deleteStudent
                studentViewModel.deleteStudentRecord(deleteStudent.id)
        }
        ) { studentViewModel.showDeleteDialog = false }

    }

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
    val expandedStates = remember { mutableStateListOf(*Array(students.size) { false }) }
    LazyColumn() {
        items(students.size) { index ->
            StudentCard(
             record = students[index],
                isExpanded = expandedStates[index],
                onExpandClick = { expandedStates[index] = !expandedStates[index] }
            )
        }
    }

}

@Composable
fun StudentCard(record: StudentRecord, onExpandClick: () -> Unit,  isExpanded: Boolean,){
    val studentViewModel: StudentViewModel = viewModel()
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {

                        Row (modifier = Modifier.fillMaxWidth()){
                            Text(
                                text = "Name: ",
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = record.name,
                                //fontWeight = FontWeight.Bold
                            )
                        }

                        Row (modifier = Modifier.fillMaxWidth()){
                            Text(
                                text = "Roll Number: ",
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = record.rollNo
                            )
                        }
                        Row (modifier = Modifier.fillMaxWidth()){
                            Text(
                                text = "Major: ",
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = record.major
                            )
                        }
                        Row (modifier = Modifier.fillMaxWidth()){
                            Text(
                                text = "Phone Number: ",
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = record.phoneNo
                            )
                        }
                    }
                    IconButton(onClick = onExpandClick) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = null, modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
                if (isExpanded) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .fillMaxWidth(),

                    ) {
                        Button(
                            onClick = { studentViewModel.showDeleteDialog(record) },
                            modifier = Modifier
                                .padding(4.dp)
                                .weight(1f)
                        ) {
                            Text("Delete")
                        }
                        Button(
                            onClick = { studentViewModel.showUpdateDialog(record) },
                            modifier = Modifier
                                .padding(4.dp)
                                .weight(1f)
                        ) {
                            Text("Update")
                        }
                    }
                }
            }
        }
    }
}
@Preview
@Composable
fun RetreiveDataPreview(){
    //StudentList(students = List<StudentRecord>)
}