package com.example.retrofitdavidcarlos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.retrofitdavidcarlos.ui.theme.RetrofitDavidCarlosTheme
import com.example.retrofitdavidcarlos.view.EntryPoint
import com.example.retrofitdavidcarlos.viewmodel.ApiViewModel
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.retrofitdavidcarlos.view.EntryPoint
import com.example.retrofitdavidcarlos.viewmodel.*

class MainActivity : ComponentActivity() {

    private val apiViewModel: ApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitDavidCarlosTheme {
                Surface (
                    color = MaterialTheme.colorScheme.background
                ){
                    val navigationController = rememberNavController()
                    EntryPoint(navigationController)
                }
            }
        }
    }
}
