package com.example.studentdatafirebase

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Pages() {
    var selectedIndexTab by remember {
        mutableStateOf(0)
    }

    val pagerState = rememberPagerState(initialPage = tabItems.size-1, pageCount = { tabItems.size })

    LaunchedEffect(selectedIndexTab){
        pagerState.animateScrollToPage(selectedIndexTab)
    }


    Column {
        TabRow(selectedTabIndex = selectedIndexTab) {
            tabItems.forEachIndexed{ index, item ->
                Tab(
                    selected = index == selectedIndexTab,
                    onClick = { selectedIndexTab = index},
                    text = { Text(text = item.title)}, icon = { Icon(
                        imageVector = item.selectedItem,
                        contentDescription =item.title
                    )
                    }
                )
            }

        }
        when (selectedIndexTab) {
            0 -> DataEntryForm()
            1 -> RetrieveDataCard()
        }
    }
}
