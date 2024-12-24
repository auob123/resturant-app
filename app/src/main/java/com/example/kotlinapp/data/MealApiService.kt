package com.kotlinapp.data

import retrofit2.http.GET

data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strCategory: String,
    val strInstructions: String,
    val strMealThumb: String
)

data class MealResponse(val meals: List<Meal>)

interface MealApiService {
    @GET("search.php?f=p")
    suspend fun getMeals(): MealResponse
}
