package com.example.recipeapp

import android.app.Activity
import android.content.Intent
import com.example.recipeapp.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import recipe.RecipeViewModel

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    RecipeListAdapter.RecipeInterface {

    private lateinit var recipeViewModel: RecipeViewModel

    private lateinit var deleteRecipeitem: MenuItem
    private lateinit var addRecipeItem: MenuItem
    private lateinit var saveRecipeListItem: MenuItem
    private lateinit var emptyTxv: TextView
    private lateinit var recyclerView: RecyclerView

    private var deleteMode: Boolean = false

    private val viewAdapter = RecipeListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emptyTxv = findViewById(R.id.emptyTxv)

        //initialize and populate spinner
        val spinner: Spinner = findViewById(R.id.recipeTypespinner)
        spinner.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            this,
            R.array.recipe_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter
        }

        //Init recycler view
        recyclerView = findViewById(R.id.recipeList)
        recyclerView.adapter = viewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // View Model
        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        recipeViewModel.readAllRecipe.observe(
            this,
            Observer { recipes -> viewAdapter.setData(recipes) })

    }

    private fun callAddRecipeActivity() {

        val intent = Intent(this, AddRecipe::class.java)

        startActivity(intent)

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {


        var category = when (parent?.getItemAtPosition(pos)) {
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

        filterList(category)

        Toast.makeText(
            parent?.context,
            parent?.getItemAtPosition(pos).toString(),
            Toast.LENGTH_SHORT
        ).show()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        deleteRecipeitem = menu!!.findItem(R.id.deleteRecipeBtn)
        addRecipeItem = menu!!.findItem(R.id.addRecipeBtn)
        saveRecipeListItem = menu.findItem(R.id.saveRecipeList)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        if (deleteMode) {

            deleteRecipeitem.isVisible = false
            addRecipeItem.isVisible = false
            saveRecipeListItem.isVisible = true

            viewAdapter.changeDeleteMode()

        } else {

            deleteRecipeitem.isVisible = true
            addRecipeItem.isVisible = true
            saveRecipeListItem.isVisible = false

            viewAdapter.changeDeleteMode()
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.deleteRecipeBtn -> {
                deleteMode = true
                this.invalidateOptionsMenu()
                return true
            }
            R.id.addRecipeBtn -> {

                callAddRecipeActivity()
                return true
            }
            R.id.saveRecipeList -> {

                deleteMode = false
                this.invalidateOptionsMenu()
                return true

            }

            else -> return super.onOptionsItemSelected(item)

        }

    }

    override fun deleteRecipe(recipeId: Int) {

        recipeViewModel.deleteRecipeById(recipeId)

    }

    private fun filterList(catId: Int) {

        recipeViewModel.loadRecipeByCategory(catId).observe(this, Observer {

                recipes ->
            if (recipes.isNotEmpty()) {
                viewAdapter.setData(recipes)
                recyclerView.visibility = View.VISIBLE
                emptyTxv.visibility = View.GONE

            } else {

                viewAdapter.setEmptyList()
                recyclerView.visibility = View.GONE
                emptyTxv.visibility = View.VISIBLE

            }
        })


    }


}

