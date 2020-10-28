package com.example.recipeapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ingredient.Ingredient
import recipe.Recipe
import recipe.RecipeViewModel
import ingredient.IngredientViewModel
import java.lang.Exception


class RecipeDetail : AppCompatActivity(), IngredientListAdapter.IngredientInterface {

    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var ingredientViewModel: IngredientViewModel

    private lateinit var editMenuItem: MenuItem
    private lateinit var saveMenuItem: MenuItem

    private lateinit var fab: FloatingActionButton
    private lateinit var recipeName: EditText
    private lateinit var recipeTitle: TextView
    private lateinit var recipeImg: ImageView
    private lateinit var editImgBtn: Button

    private lateinit var recipeCode: String
    private lateinit var currentImgUri: Uri
    private var ingredientCodeList = ArrayList<String>()

    private val viewAdapter = IngredientListAdapter(this)

    val ADD_INGREDIENT_RESULT: Int = 1
    val PICK_PHOTO_FOR_RECIPE: Int = 2

    private var recipeId: Int = 0

    private var editMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        //From Main Activity
        val intent = intent
        recipeId = intent.getIntExtra("recipe_id", -1)
        recipeCode = intent.getStringExtra("recipe_code")!!

        //Find views
        recipeTitle = findViewById(R.id.recipeTitle)
        recipeImg = findViewById(R.id.recipeImg)
        fab = findViewById(R.id.addIngredient)
        recipeName = findViewById(R.id.recipeNameEt)
        editImgBtn = findViewById(R.id.editImageBtn)

        //Init Recipe View Model
        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        if (recipeId != -1) {
            recipeViewModel.loadRecipe(recipeId).observe(this, Observer<Recipe> {

                    recipe ->
                recipeTitle.text = recipe.title
                recipeImg.setImageURI(recipe.imageUri.toUri())
                recipeName.setText(recipe.title)
                currentImgUri = recipe.imageUri.toUri()

            })

        } else {

            recipeViewModel.loadRecipeByCode(recipeCode).observe(this, Observer<Recipe> {

                    recipe ->
                recipeTitle.text = recipe.title
                recipeImg.setImageURI(recipe.imageUri.toUri())
                recipeName.setText(recipe.title)
                currentImgUri = recipe.imageUri.toUri()

            })

        }


        //Set Recycler View
        val recyclerView = findViewById<RecyclerView>(R.id.ingredientList)
        recyclerView.adapter = viewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Init Ingredient View Model
        ingredientViewModel = ViewModelProvider(this).get(IngredientViewModel::class.java)
        ingredientViewModel.loadIngredients(recipeCode).observe(this, Observer {

                ingredients ->
            viewAdapter.setData(ingredients)
            getIngredientCode(ingredients)

        })

        //To Ingredient List Page
        fab.setOnClickListener {

            val intent = Intent(this, IngredientList::class.java)

            intent.putStringArrayListExtra("ingredient_code_list", ingredientCodeList)
            startActivityForResult(intent, ADD_INGREDIENT_RESULT)

        }

        //Pick Image for Recipe
        editImgBtn.setOnClickListener {

            pickImage()

        }

    }

    private fun pickImage() {

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_PHOTO_FOR_RECIPE)

    }

    private fun getIngredientCode(ingredients: List<Ingredient>) {

        ingredients.forEach {

            ingredientCodeList.add(it.ingredientCode)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == ADD_INGREDIENT_RESULT && resultCode == Activity.RESULT_OK) {

                val ingredient = data!!.getParcelableExtra<Ingredient>("ingredient")!!

                val recipe = Ingredient(
                    ingredientCode = ingredient.ingredientCode,
                    imageUri = ingredient.imageUri,
                    name = ingredient.name,
                    recipeCode = recipeCode
                )

                ingredientViewModel.addIngredientToRecipe(recipe)

            }

            if (requestCode == PICK_PHOTO_FOR_RECIPE && resultCode == Activity.RESULT_OK) {

                recipeImg.setImageURI(data!!.data)
                currentImgUri = data.data!!
                this.contentResolver.takePersistableUriPermission(
                    currentImgUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )

            }


        } catch (ex: Exception) {


        }


    }

    private fun updateRecipe() {

        val recipeName = recipeName.text.toString()

        if (recipeName.compareTo("") != 0) {

            recipeViewModel.updateRecipe(
                Recipe(
                    id = recipeId,
                    recipeCode = recipeCode,
                    title = recipeName,
                    imageUri = currentImgUri.toString(),
                    category = 1
                )
            )

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)

        editMenuItem = menu!!.findItem(R.id.editButton)
        saveMenuItem = menu!!.findItem(R.id.saveButton)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.editButton -> {
                editMode = true
                this.invalidateOptionsMenu()
                return true
            }
            R.id.saveButton -> {

                editMode = false
                updateRecipe()
                this.invalidateOptionsMenu()
                return true
            }
            else -> return super.onOptionsItemSelected(item)

        }

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        if (editMode) {
            fab.visibility = View.VISIBLE
            recipeName.visibility = View.VISIBLE
            recipeTitle.visibility = View.INVISIBLE
            editImgBtn.visibility = View.VISIBLE
            editMenuItem.isVisible = false
            saveMenuItem.isVisible = true
            viewAdapter.changeEditMode()
        } else {
            recipeName.visibility = View.GONE
            recipeTitle.visibility = View.VISIBLE
            editImgBtn.visibility = View.GONE
            fab.visibility = View.GONE
            editMenuItem.isVisible = true
            saveMenuItem.isVisible = false
            viewAdapter.changeEditMode()
        }

        return super.onPrepareOptionsMenu(menu)

    }


    override fun deleteIngredient(ingredientCode: String) {
        ingredientViewModel.deleteIngredientFromRecipe(recipeCode, ingredientCode)
    }
}
