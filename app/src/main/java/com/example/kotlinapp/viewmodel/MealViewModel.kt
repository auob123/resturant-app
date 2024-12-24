package com.kotlinapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.kotlinapp.data.Meal
import com.kotlinapp.data.RetrofitInstance

class MealViewModel : ViewModel() {
    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals: StateFlow<List<Meal>> = _meals

    init {
        fetchMeals()
    }

    private fun fetchMeals() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getMeals()
                _meals.value = response.meals
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
