package com.example.retrofitdavidcarlos.view.medium

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.retrofitdavidcarlos.viewmodel.ApiViewModel

@Composable
fun ListasMedium(navController: NavHostController, apiViewModel: ApiViewModel){

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
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
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListasPreview(){
    ListasMedium(navController = rememberNavController(), apiViewModel = ApiViewModel())
}