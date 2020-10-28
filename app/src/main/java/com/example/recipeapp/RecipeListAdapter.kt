package com.example.recipeapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Delete
import recipe.Recipe
import kotlinx.android.synthetic.main.ingredient_item.view.*
import kotlinx.android.synthetic.main.recycler_item.view.*

class RecipeListAdapter(context: Context) :
    RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>() {

    private var recipeItemList = emptyList<Recipe>()

    private val emptyList = emptyList<Recipe>()

    private var inDelete: Boolean = false

    private val adapterCallback = context as RecipeInterface


    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val recipeTitle: TextView = itemView.recipe_title
        val recipeImg : ImageView = itemView.recipe_image
        val deleteBtn : Button = itemView.dltRecipeBtn

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)

        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {

        val currentRecipe = recipeItemList[position]

        holder.recipeTitle.text = currentRecipe.title
        holder.recipeImg.setImageURI(currentRecipe.imageUri.toUri())


        holder.itemView.setOnClickListener {


            val intent = Intent(holder.itemView.context, RecipeDetail::class.java)

            intent.putExtra("recipe_id", currentRecipe.id)
            intent.putExtra("recipe_code", currentRecipe.recipeCode)

            holder.itemView.context.startActivity(intent)

        }

        holder.deleteBtn.setOnClickListener {
            adapterCallback.deleteRecipe(currentRecipe.id)
        }

        if(inDelete) {

            holder.deleteBtn.visibility = View.GONE



        } else {

            holder.deleteBtn.visibility = View.VISIBLE

        }

    }

    override fun getItemCount() = recipeItemList.size

    fun setData(recipe : List<Recipe>) {

        this.recipeItemList = recipe
        notifyDataSetChanged()


    }

    fun setEmptyList() {

        this.recipeItemList = emptyList
        notifyDataSetChanged()

    }

    fun changeDeleteMode() {

        inDelete = !inDelete
        notifyDataSetChanged()

    }

    interface RecipeInterface {

        fun deleteRecipe(recipeId: Int)

    }

}