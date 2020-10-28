package recipe

import androidx.lifecycle.LiveData

class RecipeRepository(private val recipeDao: RecipeDao) {

    val readAllRecipe: LiveData<List<Recipe>> = recipeDao.readAllRecipe()

    fun loadRecipe(recipeId :Int) : LiveData<Recipe> {

       return recipeDao.loadRecipe(recipeId)

    }

    fun loadRecipeByCode(recipeCode: String) : LiveData<Recipe> {

        return recipeDao.loadRecipeByCode(recipeCode)

    }

    fun loadRecipeByCategory(catId : Int) : LiveData<List<Recipe>> {

        return recipeDao.loadRecipeByCategory(catId)

    }

    fun addRecipe(recipe: Recipe) {

        recipeDao.addRecipe(recipe)

    }

    fun updateRecipe(recipe: Recipe) {

        recipeDao.updateRecipe(recipe)

    }

    fun deleteRecipeById(recipeId: Int) {

        recipeDao.deleteRecipeById(recipeId)

    }

    fun deleteAllRecipes() {

        recipeDao.deleteAllRecipes()

    }

}