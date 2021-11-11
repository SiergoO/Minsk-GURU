package com.minsk.guru.screen.home.places.categories

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.minsk.guru.R
import com.minsk.guru.databinding.ItemCategoryBinding
import com.minsk.guru.domain.model.UserCategory
import kotlin.math.roundToInt

class CategoriesAdapter(
    private val context: Context?,
    private val userCategories: List<UserCategory>,
    private val categoryClickListener: CategoryClickListener? = null
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    override fun getItemCount(): Int =
        userCategories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_category,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CategoriesAdapter.ViewHolder, position: Int) {
        userCategories[position].let { userCategory ->
            holder.binding.userCategory = userCategory
            holder.bind(userCategory)
        }
    }

    private fun handleCategoryClicked(position: Int) {
        categoryClickListener?.onCategoryClicked(userCategories[position])
    }

    inner class ViewHolder(
        val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                adapterPosition.let { position ->
                    if (RecyclerView.NO_POSITION != position) {
                        handleCategoryClicked(position)
                    }
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: UserCategory) {
            binding.apply {
                val placesAmount = item.categoryPlaces.size
                val percentage =
                    (item.visitedPlaces.size / placesAmount.toDouble() * 100.0).roundToInt()
                tvPercentage.text = context?.getString(R.string.achievements_percentage, percentage)
                tvProgress.text = "${item.visitedPlaces.size}/$placesAmount"
                progressAchievements.progress = percentage
            }
        }
    }

    interface CategoryClickListener {
        fun onCategoryClicked(userCategory: UserCategory)
    }
}