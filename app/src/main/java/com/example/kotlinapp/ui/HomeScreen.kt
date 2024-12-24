package com.yourpackage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    var greeting by remember { mutableStateOf("Добро пожаловать в наш ресторан") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8E1)) // Light warm background
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title at the top
        Text(
            text = "Наш Ресторан",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFBF360C), // Darker red color for the title
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Greeting message in the center
        Text(
            text = greeting,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Button at the bottom
        Button(
            onClick = {
                greeting = "Спасибо, что посетили наш ресторан!"
            },
            modifier = Modifier.padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)) // Change this color to your desired color
        ) {
            Text(text = "Нажмите меня", color = Color.White)
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}


package com.kotlinapp.ui

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kotlinapp.viewmodel.MealViewModel
import com.yourpackage.ui.HomeScreen
import com.yourpackage.ui.MealDetailScreen
import com.yourpackage.ui.MealListScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun MainScreen(viewModel: MealViewModel) {
    val navController = rememberNavController()
    val currentDestination = navController.currentDestination?.route

    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = currentDestination == "home",
                    onClick = { navController.navigate("home") }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "List") },
                    label = { Text("List") },
                    selected = currentDestination == "list",
                    onClick = { navController.navigate("list") }
                )
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen()
            }
            composable("list") {
                val meals by viewModel.meals.collectAsState()
                MealListScreen(meals = meals) { meal ->
                    navController.navigate("detail/${meal.idMeal}")
                }
            }
            composable("detail/{mealId}") { backStackEntry ->
                val mealId = backStackEntry.arguments?.getString("mealId")
                val meal = viewModel.meals.value.find { it.idMeal == mealId }
                meal?.let { MealDetailScreen(it) }
            }
        }
    }
}

package com.yourpackage.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.kotlinapp.data.Meal

@Composable
fun MealDetailScreen(meal: Meal) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = meal.strMeal, style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = rememberImagePainter(meal.strMealThumb),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Category: ${meal.strCategory}", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Instructions:", style = MaterialTheme.typography.h6)
        Text(text = meal.strInstructions, style = MaterialTheme.typography.body1)
    }
}

package com.yourpackage.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.kotlinapp.data.Meal

@Composable
fun MealListScreen(meals: List<Meal>, onMealClick: (Meal) -> Unit) {
    LazyColumn {
        items(meals) { meal ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable { onMealClick(meal) },
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp
            ) {
                Row(modifier = Modifier.padding(8.dp)) {
                    Image(
                        painter = rememberImagePainter(meal.strMealThumb),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = meal.strMeal,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}
