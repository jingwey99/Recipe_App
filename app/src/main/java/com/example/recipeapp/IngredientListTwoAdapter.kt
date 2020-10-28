package com.example.recipeapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import ingredient.Ingredient
import kotlinx.android.synthetic.main.ingredient_item.view.*

class IngredientListTwoAdapter(context: Context) : RecyclerView.Adapter<IngredientListTwoAdapter.IngredientViewHolder>(){

    private var ingredientList = emptyList<Ingredient>()

    private val adapterCallback = context as IngredientInterface

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ingredientTitle: TextView = itemView.ingredient_title
        val ingredientImage: ImageView = itemView.ingredient_image
        val addBtn : Button = itemView.add_btn

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)


        return IngredientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentIngredient = ingredientList[position]

        holder.ingredientTitle.text = currentIngredient.name
        holder.ingredientImage.setImageURI(currentIngredient.imageUri.toUri())
        holder.addBtn.visibility = View.VISIBLE

        holder.addBtn.setOnClickListener {

            adapterCallback.addIngredientToRecipe(currentIngredient)

        }



    }

    override fun getItemCount()= ingredientList.size

    fun setData(ingredients: List<Ingredient>) {

        this.ingredientList = ingredients
        notifyDataSetChanged()

    }

    interface IngredientInterface {

        fun addIngredientToRecipe(ingredient: Ingredient)

    }

}