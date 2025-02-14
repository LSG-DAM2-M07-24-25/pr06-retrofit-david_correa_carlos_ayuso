package com.example.retrofitdavidcarlos.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import com.example.retrofitdavidcarlos.nav.Routes
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.retrofitdavidcarlos.R
import com.example.retrofitdavidcarlos.model.GameResponse
import com.example.retrofitdavidcarlos.model.Game
import com.example.retrofitdavidcarlos.viewmodel.ApiViewModel

@Composable
fun Juegos(navController: NavHostController, apiViewModel: ApiViewModel){

}