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
import java.lang.ClassCastException

class IngredientListAdapter(context: Context) : RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder>() {

    private var ingredientList = emptyList<Ingredient>()

    private var inEdit: Boolean = false

    private val adapterCallback = context as IngredientInterface

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ingredientTitle: TextView = itemView.ingredient_title
        val ingredientImage: ImageView = itemView.ingredient_image
        val deleteBtn: Button = itemView.delete_btn

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

        holder.deleteBtn.setOnClickListener {

                adapterCallback.deleteIngredient(currentIngredient.ingredientCode)
                notifyDataSetChanged()
        }

        if (inEdit) {

            holder.deleteBtn.visibility = View.GONE

        } else {

            holder.deleteBtn.visibility = View.VISIBLE

        }

    }

    override fun getItemCount() = ingredientList.size

    fun setData(ingredients: List<Ingredient>) {

        this.ingredientList = ingredients
        notifyDataSetChanged()

    }

    fun changeEditMode() {

        inEdit = !inEdit
        notifyDataSetChanged()

    }

    interface IngredientInterface {

        fun deleteIngredient(ingredientCode: String)

    }

}