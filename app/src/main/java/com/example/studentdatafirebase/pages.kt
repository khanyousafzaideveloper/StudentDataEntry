package com.example.studentdatafirebase

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Pages() {
    val navController:NavHostController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val currentScreen = currentDestination?.route ?: StudentRecordScreens.RetrievedData.name
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if(currentScreen==StudentRecordScreens.RetrievedData.name){
                        Text("Student Records")
                    }
                    else if(currentScreen==StudentRecordScreens.AddData.name){
                        Text("Add Student Record")
                    }
                },

                navigationIcon = {
                    if(currentScreen==StudentRecordScreens.AddData.name){
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                    }
                }
            )
        },
        floatingActionButton = {
            if (currentScreen == StudentRecordScreens.RetrievedData.name) {
                FloatingActionButton(
                    onClick = { navController.navigate(StudentRecordScreens.AddData.name) },
                ) {
                    Row() {
                        Text(text = "Add Student", fontWeight = FontWeight(700), fontSize = 15.sp, modifier = Modifier.align(Alignment.CenterVertically) .padding(start= 8.dp))
                        Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(30.dp) .padding(end =4.dp) .align(Alignment.CenterVertically))
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = {  innerPadding ->
            NavHost(
                navController = navController,
                startDestination = StudentRecordScreens.RetrievedData.name,
                modifier = Modifier.padding(innerPadding)
            ){
                composable(route = StudentRecordScreens.AddData.name){
                    DataEntryForm()
                }
                composable(route = StudentRecordScreens.RetrievedData.name){
                    RetrieveDataCard()
                }
            }
        }
    )

}

enum class StudentRecordScreens{
    AddData,
    RetrievedData
}
