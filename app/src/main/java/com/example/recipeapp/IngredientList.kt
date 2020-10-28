package com.example.recipeapp

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ingredient.Ingredient
import ingredient.IngredientViewModel


class IngredientList : AppCompatActivity(), IngredientListTwoAdapter.IngredientInterface {

    private lateinit var ingredientViewModel: IngredientViewModel

    private val viewAdapter = IngredientListTwoAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_list)

        val intent = intent
        val ingredientCodeList = intent.getStringArrayListExtra("ingredient_code_list")!!.toList()

        //Set Recycler View
        val recyclerView = findViewById<RecyclerView>(R.id.fullIngredientList)
        recyclerView.adapter = viewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Init Ingredient View Model
        ingredientViewModel = ViewModelProvider(this).get(IngredientViewModel::class.java)


        ingredientViewModel.loadIngredientWithoutRecipe(ingredientCodeList).observe(this, Observer {

                ingredients ->
            viewAdapter.setData(ingredients)

        })

    }

    override fun addIngredientToRecipe(ingredient: Ingredient) {
        val intent = intent
        intent.putExtra("ingredient", ingredient)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }


}
