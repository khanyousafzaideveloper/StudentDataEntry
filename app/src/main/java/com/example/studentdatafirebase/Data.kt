package com.example.studentdatafirebase

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel



data class TabItem(
    val title: String,
    val selectedItem : ImageVector,
)
val tabItems  = listOf(
    TabItem(
        title = "Submit Data",
        selectedItem = Icons.Filled.Add,
    ),
    TabItem(
        title = "Retrieved Data",
        selectedItem = Icons.Filled.Favorite,
    ),
)