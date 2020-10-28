package com.example.recipeapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_add_recipe.*
import recipe.Recipe
import recipe.RecipeViewModel
import java.lang.Exception

class AddRecipe : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var recipeViewModel: RecipeViewModel

    private lateinit var newRecipeImg: ImageView
    private lateinit var newRecipeNameEt: EditText
    private lateinit var newRecipeCodeEt: EditText
    private lateinit var newImgBtn: Button
    private lateinit var catSpinner: Spinner
    private lateinit var nextBtn: Button

    val PICK_PHOTO_FOR_RECIPE: Int = 2

    private var recipeCode: String = ""
    private var recipeName: String = ""

    private var category: Int = 0

    private var defaultPicUri: String =
        "android.resource://com.example.recipeapp/drawable/" + R.drawable.x

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        //Find Views
        newRecipeImg = findViewById(R.id.newRecipeImg)
        newRecipeNameEt = findViewById(R.id.newRecipeNameEt)
        newImgBtn = findViewById(R.id.editNewImageBtn)
        catSpinner = findViewById(R.id.catSpinner)
        nextBtn = findViewById(R.id.nextBtn)
        newRecipeCodeEt = findViewById(R.id.newRecipeCodeEt)

        //initialize and populate spinner
        val spinner: Spinner = findViewById(R.id.catSpinner)
        spinner.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            this,
            R.array.recipe_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        //Init Recipe View Model
        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        //TextView Listeners
        newRecipeNameEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                recipeName = s.toString()


            }

        })

        newRecipeCodeEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                recipeCode = s.toString()
            }


        })

        //Pick Image
        editNewImageBtn.setOnClickListener {

            pickImage()

        }

        nextBtn.setOnClickListener {

            if (recipeCode != "" && recipeName != "") {

                addRecipe()

                val intent = Intent(this, RecipeDetail::class.java)

                intent.putExtra("recipe_code", recipeCode)

                startActivity(intent)

                finish()


            } else {

                Toast.makeText(
                    this,
                    "Fill in the blanks.",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        try {
            super.onActivityResult(requestCode, resultCode, data)

            //From pick image activity
            if (requestCode == PICK_PHOTO_FOR_RECIPE && resultCode == Activity.RESULT_OK) {

                newRecipeImg.setImageURI(data!!.data)
                defaultPicUri = data.data.toString()
                this.contentResolver.takePersistableUriPermission(
                    defaultPicUri.toUri(),
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )

            }
        } catch (ex: Exception) {

            print(ex)

        }


    }

    private fun pickImage() {

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_PHOTO_FOR_RECIPE)

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        category = when (parent?.getItemAtPosition(position)) {
            "Breakfasts" -> {
                1
            }
            "Dinner" -> {
                2
            }
            else -> {
                3
            }
        }

        Toast.makeText(
            parent?.context,
            parent?.getItemAtPosition(position).toString(),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    private fun addRecipe() {

        recipeViewModel.addRecipe(
            Recipe(
                recipeCode = recipeCode,
                title = recipeName,
                category = category,
                imageUri = defaultPicUri
            )
        )

    }

}
