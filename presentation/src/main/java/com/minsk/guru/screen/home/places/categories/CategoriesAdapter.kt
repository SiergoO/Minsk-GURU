package com.minsk.guru.screen.home.places.categories

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minsk.guru.R
import com.minsk.guru.databinding.ItemCategoryBinding
import com.minsk.guru.domain.model.Category
import com.minsk.guru.domain.model.Place
import kotlin.math.roundToInt

class CategoriesAdapter(
    private val context: Context?,
    callback: Callback? = null
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    var callback: Callback? = null
    private val layoutInflater = LayoutInflater.from(context)
    private var categories: List<Category> = listOf()
    private var visitedPlaces: List<Place> = listOf()

    init {
        this.callback = callback
    }

    fun setCategories(items: List<Category>) {
        this.categories = items
        notifyDataSetChanged()
    }

    fun setVisitedPlaces(items: List<Place>) {
        this.visitedPlaces = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =
        categories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: CategoriesAdapter.ViewHolder, position: Int) {
        categories[position].let { category ->
            holder.bind(category, visitedPlaces.filter { it.category.contains(category.name) })
        }
    }

    private fun handleItemClicked(position: Int) {
        val category = categories[position]
        callback?.onCategoryClicked(category)
    }


    inner class ViewHolder(
        private val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                adapterPosition.let { position ->
                    if (RecyclerView.NO_POSITION != position) {
                        handleItemClicked(position)
                    }
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: Category, visitedPlaces: List<Place>) {
            binding.apply {
                val placesAmount = item.placesIds.size
                val percentage = (visitedPlaces.size / placesAmount.toDouble() * 100.0).roundToInt()
                category.text = item.name
                tvPercentage.text = context?.getString(R.string.achievements_percentage, percentage)
                tvProgress.text = "${visitedPlaces.size}/$placesAmount"
                progressAchievements.progress = percentage
            }
        }
    }

    interface Callback {
        fun onCategoryClicked(category: Category)
    }
}