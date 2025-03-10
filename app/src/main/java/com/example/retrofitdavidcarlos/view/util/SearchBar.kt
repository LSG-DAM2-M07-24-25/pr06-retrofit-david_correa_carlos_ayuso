package com.example.retrofitdavidcarlos.view.util

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
//import com.example.retrofitdavidcarlos.view.compact.MySearchBarView
import com.example.retrofitdavidcarlos.viewmodel.SearchBarViewModel

@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(myViewModel: SearchBarViewModel){
    //val searchHistory by myViewModel.searchHistory.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        //MySearchBarView(myViewModel)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
        ) {
            /*items(searchHistory) { search ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = search,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }*/
        }
    }
}

