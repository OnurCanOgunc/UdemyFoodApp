package com.decode.udemyfoodapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.decode.udemyfoodapp.data.DataStoreRepository
import com.decode.udemyfoodapp.util.Constants.API_KEY
import com.decode.udemyfoodapp.util.Constants.DEFAULT_DIET_TYPE
import com.decode.udemyfoodapp.util.Constants.DEFAULT_MEAL_TYPE
import com.decode.udemyfoodapp.util.Constants.DEFAULT_RECIPES_NUMBER
import com.decode.udemyfoodapp.util.Constants.QUERY_ADD_RECIPE_INFORMATION
import com.decode.udemyfoodapp.util.Constants.QUERY_API_KEY
import com.decode.udemyfoodapp.util.Constants.QUERY_DIET
import com.decode.udemyfoodapp.util.Constants.QUERY_FILL_INGREDIENTS
import com.decode.udemyfoodapp.util.Constants.QUERY_NUMBER
import com.decode.udemyfoodapp.util.Constants.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(
                mealType,
                mealTypeId,
                dietType,
                dietTypeId
            )
        }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

}
