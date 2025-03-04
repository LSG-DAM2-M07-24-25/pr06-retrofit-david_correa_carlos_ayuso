package com.example.retrofitdavidcarlos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.retrofitdavidcarlos.ui.theme.RetrofitDavidCarlosTheme
import com.example.retrofitdavidcarlos.view.EntryPoint
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.retrofitdavidcarlos.viewmodel.ApiViewModel
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.retrofitdavidcarlos.viewmodel.ListViewModel
import com.example.retrofitdavidcarlos.viewmodel.RoomViewModel

class MainActivity : ComponentActivity() {
    private val apiViewModel: ApiViewModel by viewModels()
    private val listaViewModel: ListViewModel by viewModels()
    private val roomViewModel: RoomViewModel by viewModels()
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitDavidCarlosTheme {
                Surface (
                    color = MaterialTheme.colorScheme.background
                ){
                    val navigationController = rememberNavController()
                    val windowSize = calculateWindowSizeClass(this)
                    EntryPoint(navigationController, apiViewModel, windowSize, listaViewModel, roomViewModel)
                }
            }
        }
    }
}
