package ingredient

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addIngredient(ingredient: Ingredient): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addIngredients(ingredientList: List<Ingredient>): List<Long>

    @Update
    fun updateIngredient(ingredient: Ingredient)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateIngredients(ingredientList: List<Ingredient>)

    @Delete
    fun deleteIngredient(ingredient: Ingredient)

    @Query("DELETE FROM ingredient_table WHERE recipeCode = :recipeCode AND ingredientCode = :ingredientCode ")
    fun deleteIngredientfromRecipe(recipeCode: String, ingredientCode: String)

    @Query("DELETE FROM ingredient_table")
    fun deleteAllIngredients()

    @Query("SELECT * FROM ingredient_table WHERE recipeCode = :recipeCode")
    fun loadIngredients(recipeCode: String): LiveData<List<Ingredient>>

    @Query("SELECT * FROM ingredient_table GROUP BY ingredientCode")
    fun loadAllIngredients(): LiveData<List<Ingredient>>

    @Query("SELECT * FROM ingredient_table WHERE ingredientCode NOT IN (:ingredients) GROUP BY ingredientCode")
    fun loadAllIngredientWithoutRecipe(ingredients: List<String>): LiveData<List<Ingredient>>

    @Transaction
    fun upsert(ingredient: Ingredient) {
        val id: Long = addIngredient(ingredient)
        if (id == -1L) {
            updateIngredient(ingredient)
        }
    }

    @Transaction
    fun upsert(ingredientList: List<Ingredient>) {
        val insertResult: List<Long> = addIngredients(ingredientList)
        val updateList: MutableList<Ingredient> = ArrayList()
        for (i in insertResult.indices) {
            if (insertResult[i].compareTo(-1) == 0) {
                updateList.add(ingredientList[i])
            }
        }
        if (updateList.isNotEmpty()) {
            updateList.forEach { updateIngredient(it) }
        }
    }

}