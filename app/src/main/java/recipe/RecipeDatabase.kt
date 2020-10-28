package recipe

import android.content.Context
import android.net.Uri
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.recipeapp.R
import ingredient.Ingredient
import ingredient.IngredientDao
import ioThread

@Database(entities = [Recipe::class, Ingredient::class], version = 1, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao

    companion object {

        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context)
                        .also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RecipeDatabase::class.java,
            "recipe_database"
        ).addCallback(object : Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                ioThread {

                    getDatabase(context).recipeDao().upsert(
                        PREPOPULATE_RECIPE
                    )
                    getDatabase(context).ingredientDao().upsert(
                        PREPOPULATE_INGREDIENTS
                    )

                }
            }
        }).build()

        val PREPOPULATE_RECIPE =
            listOf<Recipe>(
                Recipe(
                    0,
                    "R001",
                    "Bread With Eggs",
                    "android.resource://com.example.recipeapp/drawable/" + R.drawable.breadwithegg,
                    1

                ),
                Recipe(
                    0,
                    "R002",
                    "Cereal with cream",
                    "android.resource://com.example.recipeapp/drawable/" + R.drawable.cereal,
                    1
                ),
                Recipe(
                    0,
                    "R003",
                    "Pancakes",
                    "android.resource://com.example.recipeapp/drawable/" + R.drawable.pancake,
                    1
                ),
                Recipe(
                    0,
                    "R004",
                    "Wraps",
                    "android.resource://com.example.recipeapp/drawable/" + R.drawable.wrap,
                    1
                ),
                Recipe(
                    0,
                    "R005",
                    "English Breakfast",
                    "android.resource://com.example.recipeapp/drawable/" + R.drawable.englishbreakfast,
                    1
                ),
                Recipe(
                    0,
                    "R006",
                    "Chicken With Vegs",
                    "android.resource://com.example.recipeapp/drawable/" + R.drawable.chicken,
                    2
                ),
                Recipe(
                    0,
                    "R007",
                    "Roasted Chicken",
                    "android.resource://com.example.recipeapp/drawable/" + R.drawable.roastedchicken,
                    2
                ),
                Recipe(
                    0,
                    "R008",
                    "Scallops",
                    "android.resource://com.example.recipeapp/drawable/" + R.drawable.scallop,
                    2
                ),
                Recipe(
                    0,
                    "R009",
                    "Spaghetti with Meatballs",
                    "android.resource://com.example.recipeapp/drawable/" + R.drawable.spaghetti,
                    2
                ),
                Recipe(
                    0,
                    "R010",
                    "Lasagna",
                    "android.resource://com.example.recipeapp/drawable/" + R.drawable.lagsana,
                    2
                ),
                Recipe(
                    0,
                    "R011",
                    "Cake",
                    "android.resource://com.example.recipeapp/drawable/" + R.drawable.cake,
                    3
                ),
                Recipe(
                    0,
                    "R012",
                    "Cupcakes",
                    "android.resource://com.example.recipeapp/drawable/" + R.drawable.cupcake,
                    3
                ),
                Recipe(
                    0,
                    "R013",
                    "Lava Cake",
                    "android.resource://com.example.recipeapp/drawable/" + R.drawable.lavacake,
                    3
                ),
                Recipe(
                    0,
                    "R014",
                    "Chocolate Pear",
                    "android.resource://com.example.recipeapp/drawable/" + R.drawable.pearchocolate,
                    3
                ),
                Recipe(
                    0,
                    "R015",
                    "Pudding",
                    "android.resource://com.example.recipeapp/drawable/" + R.drawable.pudding,
                    3
                )

            )

        val PREPOPULATE_INGREDIENTS = listOf<Ingredient>(
            // R001 - Bread with eggs
            Ingredient(
                0,
                "I001",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.bread,
                "Bread",
                "R001"
            ),
            Ingredient(
                0,
                "I002",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.egg,
                "Egg",
                "R001"
            ),
            Ingredient(
                0,
                "I003",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.salt,
                "Salt",
                "R001"
            ),
            Ingredient(
                0,
                "I004",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.pepper,
                "Pepper",
                "R001"
            ),
            // Cereal with cream - R002
            Ingredient(
                0,
                "I005",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.cereal,
                "Cereal",
                "R002"
            ),
            Ingredient(
                0,
                "I006",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.milk,
                "Milk",
                "R002"
            ),
            Ingredient(
                0,
                "I007",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.cream,
                "Cream",
                "R002"
            ),
            // Pancakes - R003
            Ingredient(
                0,
                "I008",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.flour,
                "Flour",
                "R003"
            ),
            Ingredient(
                0,
                "I006",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.milk,
                "Milk",
                "R003"
            ),
            Ingredient(
                0,
                "I007",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.salt,
                "Salt",
                "R003"
            ),
            Ingredient(
                0,
                "I009",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.butter,
                "Butter",
                "R003"
                //Wraps - R004
            ),
            Ingredient(
                0,
                "I010",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.raw_wrap,
                "Wrap",
                "R004"
            ),
            Ingredient(
                0,
                "I002",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.egg,
                "Egg",
                "R004"
            ),
            Ingredient(
                0,
                "I009",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.butter,
                "Butter",
                "R004"
            ),
            Ingredient(
                0,
                "I003",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.salt,
                "Salt",
                "R004"
            ),
            Ingredient(
                0,
                "I011",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.strawberries,
                "Strawberries",
                "R004"
            ),
            // English Breakfast - R005
            Ingredient(
                0,
                "I012",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.sausage,
                "Sausage",
                "R005"
            ),
            Ingredient(
                0,
                "I002",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.egg,
                "Egg",
                "R005"
            ),
            Ingredient(
                0,
                "I013",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.mincedmeat,
                "Minced Meat",
                "R005"
            ),
            Ingredient(
                0,
                "I014",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.bacon,
                "Bacon",
                "R005"
            ),
            Ingredient(
                0,
                "I015",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.tomato,
                "Tomato",
                "R005"
            ),
            Ingredient(
                0,
                "I016",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.bakedbeans,
                "Baked Beans",
                "R005"
            ),
            // Chicken with Vegs - R006
            Ingredient(
                0,
                "I017",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.raw_chicken,
                "Raw Chicken",
                "R006"
            ),
            Ingredient(
                0,
                "I018",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.broccoli,
                "Broccoli",
                "R006"
            ),
            Ingredient(
                0,
                "I019",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.carrot,
                "Carrot",
                "R006"
            ),
            Ingredient(
                0,
                "I015",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.tomato,
                "Tomato",
                "R006"
            ),
            // Roasted Chicken - R007
            Ingredient(
                0,
                "I017",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.raw_chicken,
                "Raw Chicken",
                "R007"
            ),
            Ingredient(
                0,
                "I020",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.potato,
                "Potato",
                "R007"
            ),
            Ingredient(
                0,
                "I021",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.thyme,
                "Thyme",
                "R007"
            ),
            Ingredient(
                0,
                "I022",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.currypowder,
                "Curry Powder",
                "R007"
            ),
            // Scallop - R008
            Ingredient(
                0,
                "I023",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.raw_scallop,
                "Raw Scallop",
                "R008"
            ),
            Ingredient(
                0,
                "I003",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.salt,
                "Salt",
                "R008"
            ),
            Ingredient(
                0,
                "I004",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.pepper,
                "Pepper",
                "R008"
            ),
            Ingredient(
                0,
                "I021",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.thyme,
                "Thyme",
                "R008"
            ),
            Ingredient(
                0,
                "I009",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.butter,
                "Butter",
                "R008"
            ),
            //Spaghetti with meatballs - R009
            Ingredient(
                0,
                "I024",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.rawspagethhi,
                "Spaghetti",
                "R009"
            ),
            Ingredient(
                0,
                "I013",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.mincedmeat,
                "Minced Meat",
                "R009"
            ),
            Ingredient(
                0,
                "I025",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.tomatosauce,
                "Tomato",
                "R009"
            ),
            Ingredient(
                0,
                "I006",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.milk,
                "Milk",
                "R009"
            ),
            Ingredient(
                0,
                "I003",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.salt,
                "Salt",
                "R009"
            ),
            //Lasagna - R010
            Ingredient(
                0,
                "I008",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.flour,
                "Flour",
                "R010"
            ),
            Ingredient(
                0,
                "I025",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.tomatosauce,
                "Tomato Sauce",
                "R010"
            ),
            Ingredient(
                0,
                "I013",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.mincedmeat,
                "Minced Meat",
                "R010"
            ),
            Ingredient(
                0,
                "I021",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.thyme,
                "Thyme",
                "R010"
            ),
            Ingredient(
                0,
                "I009",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.butter,
                "Butter",
                "R010"
            ),
            Ingredient(
                0,
                "I003",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.salt,
                "Salt",
                "R010"
            ),
            //Cake - R011
            Ingredient(
                0,
                "I003",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.salt,
                "Salt",
                "R011"
            ),
            Ingredient(
                0,
                "I009",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.butter,
                "Butter",
                "R011"
            ),
            Ingredient(
                0,
                "I008",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.flour,
                "Flour",
                "R011"
            ),
            Ingredient(
                0,
                "I007",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.cream,
                "Cream",
                "R011"
            ),
            Ingredient(
                0,
                "I026",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.water,
                "Water",
                "R011"
            ),
            //Cupcake - R012
            Ingredient(
                0,
                "I003",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.salt,
                "Salt",
                "R012"
            ),
            Ingredient(
                0,
                "I009",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.butter,
                "Butter",
                "R012"
            ),
            Ingredient(
                0,
                "I008",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.flour,
                "Flour",
                "R012"
            ),
            Ingredient(
                0,
                "I007",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.cream,
                "Cream",
                "R012"
            ),
            Ingredient(
                0,
                "I026",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.water,
                "Water",
                "R012"
            ),
            //Lava cake - R013
            Ingredient(
                0,
                "I003",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.salt,
                "Salt",
                "R013"
            ),
            Ingredient(
                0,
                "I009",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.butter,
                "Butter",
                "R013"
            ),
            Ingredient(
                0,
                "I008",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.flour,
                "Flour",
                "R013"
            ),
            Ingredient(
                0,
                "I007",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.cream,
                "Cream",
                "R013"
            ),
            Ingredient(
                0,
                "I026",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.water,
                "Water",
                "R013"
            ),
            Ingredient(
                0,
                "I027",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.chocolate,
                "Chocolate",
                "R013"
            ),
            //Chocolate Pear - R014
            Ingredient(
                0,
                "I028",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.pear,
                "Pear",
                "R014"
            ),
            Ingredient(
                0,
                "I027",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.chocolate,
                "Chocolate",
                "R013"
            ),
            // Pudding - R015
            Ingredient(
                0,
                "I003",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.salt,
                "Salt",
                "R015"
            ),
            Ingredient(
                0,
                "I009",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.butter,
                "Butter",
                "R015"
            ),
            Ingredient(
                0,
                "I008",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.flour,
                "Flour",
                "R015"
            ),
            Ingredient(
                0,
                "I007",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.cream,
                "Cream",
                "R015"
            ),
            Ingredient(
                0,
                "I026",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.water,
                "Water",
                "R015"
            ),
            Ingredient(
                0,
                "I027",
                "android.resource://com.example.recipeapp/drawable/" + R.drawable.chocolate,
                "Chocolate",
                "R015"
            )
        )

    }


}