package ingredient

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import recipe.RecipeDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IngredientViewModel(application: Application) : AndroidViewModel(application) {


    private val repository: IngredientRepository

    init {
        val ingredientDao = RecipeDatabase.getDatabase(application).ingredientDao()
        repository = IngredientRepository(ingredientDao)

    }

    fun loadAllIngredients() : LiveData<List<Ingredient>> {

        return repository.loadAllIngredient()

    }

    fun loadIngredients(recipeCode: String): LiveData<List<Ingredient>> {


        return repository.loadIngredients(recipeCode)

    }

    fun deleteIngredientFromRecipe(recipeCode: String, ingredientCode: String) {

        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteIngredientFromRecipe(recipeCode, ingredientCode)
        }


    }

    fun loadIngredientWithoutRecipe(ingredientCodes: List<String>) : LiveData<List<Ingredient>> {

        return repository.loadIngredientsWithoutRecipe(ingredientCodes)

    }

    fun addIngredientToRecipe(ingredient: Ingredient) {

        viewModelScope.launch(Dispatchers.IO) {

            repository.addIngredientToRecipe(ingredient)

        }

    }


}