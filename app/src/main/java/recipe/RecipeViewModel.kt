package recipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application){

    val readAllRecipe: LiveData<List<Recipe>>
    private val repository: RecipeRepository

    init {
        val recipeDao = RecipeDatabase.getDatabase(application).recipeDao()
        repository = RecipeRepository(recipeDao)
        readAllRecipe = repository.readAllRecipe
    }

    fun loadRecipe(recipeId: Int) : LiveData<Recipe>{

        return  repository.loadRecipe(recipeId)

    }

    fun loadRecipeByCode(recipeCode: String) : LiveData<Recipe> {

        return repository.loadRecipeByCode(recipeCode)

    }

    fun loadRecipeByCategory(catId : Int) : LiveData<List<Recipe>> {

        return repository.loadRecipeByCategory(catId)

    }

    fun addRecipe(recipe: Recipe) {

        viewModelScope.launch(Dispatchers.IO) {
            repository.addRecipe(recipe)
        }

    }

    fun updateRecipe(recipe: Recipe) {

        viewModelScope.launch(Dispatchers.IO) {

            repository.updateRecipe(recipe)

        }

    }

    fun deleteRecipe(recipe: Recipe) {

        viewModelScope.launch(Dispatchers.IO) {

            repository.updateRecipe(recipe)

        }

    }

    fun deleteRecipeById(recipeId: Int) {

        viewModelScope.launch(Dispatchers.IO) {

            repository.deleteRecipeById(recipeId)

        }

    }

}