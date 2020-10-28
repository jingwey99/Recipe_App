package ingredient

import androidx.lifecycle.LiveData

class IngredientRepository(private val ingredientDao: IngredientDao) {


    fun loadIngredients(recipeCode: String): LiveData<List<Ingredient>> {

        return ingredientDao.loadIngredients(recipeCode)

    }

    fun deleteIngredientFromRecipe(recipeCode: String, ingredientCode: String) {

        return ingredientDao.deleteIngredientfromRecipe(recipeCode, ingredientCode)

    }

    fun loadAllIngredient(): LiveData<List<Ingredient>> {

        return ingredientDao.loadAllIngredients()

    }

    fun loadIngredientsWithoutRecipe(ingredientCodes: List<String>): LiveData<List<Ingredient>> {

        return ingredientDao.loadAllIngredientWithoutRecipe(ingredientCodes)

    }

    fun addIngredientToRecipe(ingredient: Ingredient): Long {

        return ingredientDao.addIngredient(ingredient)

    }


}