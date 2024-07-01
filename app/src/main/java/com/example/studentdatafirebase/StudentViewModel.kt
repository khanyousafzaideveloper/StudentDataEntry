package com.example.studentdatafirebase

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


sealed interface StudentRecordUiState {
    data class Success(val studentRecordList: List<StudentRecord>) :  StudentRecordUiState
    object Error :  StudentRecordUiState
    object Loading :  StudentRecordUiState
}

data class StudentRecord(
    val id: String ="",
    val name: String = "",
    val rollNo: String = "",
    val major: String="",
    val phoneNo:String=""
){constructor() : this("","", "", "", "")}
class StudentViewModel: ViewModel(){

    var name by mutableStateOf("")
    var rollNo by mutableStateOf("")
    var major by  mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    val db = Firebase.firestore


    var showUpdateDialog by mutableStateOf(false)
    var studentToUpdate by mutableStateOf(StudentRecord())

    private val _studentRecordUiState = mutableStateOf<StudentRecordUiState>(StudentRecordUiState.Loading)
    val studentRecordUiState: State<StudentRecordUiState> = _studentRecordUiState

    fun onSubmitForm(onSuccess: () -> Unit, onError: (String) -> Unit){


        val student = hashMapOf(
            "name" to name,
            "rollNo" to rollNo,
            "major" to major,
            "phoneNo" to phoneNumber
        )
        db.collection("Students")
            .add(student)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                onSuccess()
                name = ""
                rollNo =""
                major = ""
                phoneNumber = ""
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                error(e)
            }
    }


    fun deleteStudentRecord(studentId: String) {
        val db = Firebase.firestore
        db.collection("Students").document(studentId)
            .delete()
            .addOnSuccessListener {
                Log.d("Firestore", "DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error deleting document", e)
            }
    }

    init {
        retrieveStudentData()
    }
    fun retrieveStudentData(){
        viewModelScope.launch {
            try {
                db.collection("Students").addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w(ContentValues.TAG, "Error getting documents", e)
                        _studentRecordUiState.value = StudentRecordUiState.Error
                        return@addSnapshotListener
                    }

                    if (snapshot != null && !snapshot.isEmpty) {
                        val studentList = snapshot.documents.map { document ->
                            val student = document.toObject(StudentRecord::class.java)!!
                            student.copy(id = document.id)
                        }
                        _studentRecordUiState.value = StudentRecordUiState.Success(studentList)
                    } else {
                        _studentRecordUiState.value = StudentRecordUiState.Success(emptyList())
                    }
                }
            } catch (exception: Exception) {
                Log.w(ContentValues.TAG, "Error getting documents", exception)
                _studentRecordUiState.value = StudentRecordUiState.Error
            }
        }
    }

    fun updateStudentRecord() {
        val studentMap = hashMapOf(
            "name" to studentToUpdate.name,
            "rollNo" to studentToUpdate.rollNo,
            "major" to studentToUpdate.major,
            "phoneNo" to studentToUpdate.phoneNo
        )

        db.collection("Students").document(studentToUpdate.id)
            .set(studentMap)
            .addOnSuccessListener {
                Log.d("Firestore", "DocumentSnapshot successfully updated!")
                showUpdateDialog = false
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error updating document", e)
            }
    }


    fun showUpdateDialog(student: StudentRecord) {
        studentToUpdate = student
        showUpdateDialog = true
    }
}