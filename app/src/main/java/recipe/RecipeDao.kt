package recipe

import android.provider.SyncStateContract.Helpers.update
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addRecipe(recipe: Recipe): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllRecipes(recipeList: List<Recipe>): List<Long>

    @Update
    fun updateRecipe(recipe: Recipe)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRecipe(recipeList: List<Recipe>)

    @Delete
    fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe_table ORDER BY id ASC")
    fun readAllRecipe(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipe_table WHERE id = :recipeId")
    fun loadRecipe(recipeId : Int) : LiveData<Recipe>

    @Query("SELECT * FROM recipe_table WHERE recipeCode = :recipeCode")
    fun loadRecipeByCode(recipeCode :String) : LiveData<Recipe>

    @Query("SELECT * FROM recipe_table WHERE category = :categoryId")
    fun loadRecipeByCategory(categoryId : Int): LiveData<List<Recipe>>

    @Query("DELETE FROM recipe_table")
    fun deleteAllRecipes()

    @Query("DELETE FROM recipe_table WHERE id = :recipeId")
    fun deleteRecipeById(recipeId: Int)

    @Transaction
    fun upsert(recipe: Recipe) {
        val id: Long = addRecipe(recipe)
        if (id == -1L) {
            updateRecipe(recipe)
        }
    }

    @Transaction
    fun upsert(recipeList: List<Recipe>) {
        val insertResult: List<Long> = addAllRecipes(recipeList)
        val updateList: MutableList<Recipe> = ArrayList()
        for (i in insertResult.indices) {
            if (insertResult[i].compareTo(-1) == 0) {
                updateList.add(recipeList[i])
            }
        }
        if (updateList.isNotEmpty()) {
            updateList.forEach { updateRecipe(it) }
        }
    }

}