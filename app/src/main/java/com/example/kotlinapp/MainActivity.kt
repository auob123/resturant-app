package com.example.kotlinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kotlinapp.ui.MainScreen
import com.kotlinapp.viewmodel.MealViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MealViewModel = viewModel()
            MainScreen(viewModel = viewModel)
        }
    }
}
