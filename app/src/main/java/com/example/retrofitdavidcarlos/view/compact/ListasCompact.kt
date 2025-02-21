package com.example.retrofitdavidcarlos.view.compact

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.retrofitdavidcarlos.model.GameResponse
import com.example.retrofitdavidcarlos.viewmodel.ApiViewModel

@Composable
fun ListasCompact(navController: NavHostController, apiViewModel: ApiViewModel){
    val games: GameResponse by apiViewModel.games.observeAsState(GameResponse(emptyList()))
    var tabSeleccionado by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        apiViewModel.getGames()
    }
    Scaffold(
        topBar = { Topbar(tabSeleccionado){ tabSeleccionado = it} },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        ContenidoPrincipal(
            games = games,
            paddingValues = padding
        )
    }
}

@Composable
fun Topbar(tabSeleccionado: Int, onTabSelected: (Int) -> Unit){

}

@Composable
fun ContenidoPrincipal(games: GameResponse, paddingValues: PaddingValues){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clickable {

                }
        ){
            Text(
                text = "Por Jugar",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(16.dp),
            )
        }

        Spacer(modifier = Modifier.size(30.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clickable {

                }
        ){
            Text(
                text = "Jugados",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(16.dp),
            )
        }

        Spacer(modifier = Modifier.size(30.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clickable {

                }
        ){
            Text(
                text = "Jugando",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(16.dp),
            )
        }

        Spacer(modifier = Modifier.size(30.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clickable {

                }
        ){
            Text(
                text = "Favoritos",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListasPreview(){
    ListasCompact(navController = rememberNavController(), apiViewModel = ApiViewModel())
}