package recipe

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_table")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val recipeCode : String,

    val title: String,

    val imageUri : String,

    val category: Int

    /*categories :
    Breakfasts = 1
    Dinner = 2
    Dessert = 3
    */

)