package com.minsk.guru.screen.home.places.categories

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.paging.DatabasePagingOptions
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter
import com.minsk.guru.R
import com.minsk.guru.databinding.ItemCategoryBinding
import com.minsk.guru.domain.model.UserCategory
import kotlin.math.roundToInt

class CategoriesAdapter(
    options: DatabasePagingOptions<UserCategory>,
    private val context: Context?,
    private val categoryClickListener: CategoryClickListener? = null
) : FirebaseRecyclerPagingAdapter<UserCategory, CategoriesAdapter.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_category,
                parent,
                false
            )
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int, userCategory: UserCategory) {
        viewHolder.binding.userCategory = userCategory
        viewHolder.bind(userCategory)
    }

    private fun handleCategoryClicked(userCategory: UserCategory) {
        categoryClickListener?.onCategoryClicked(userCategory)
    }

    inner class ViewHolder(
        val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(userCategory: UserCategory) {
            binding.apply {
                val placesAmount = userCategory.categoryPlaces.size
                val percentage =
                    (userCategory.visitedPlaces.size / placesAmount.toDouble() * 100.0).roundToInt()
                tvPercentage.text = context?.getString(R.string.achievements_percentage, percentage)
                tvProgress.text = "${userCategory.visitedPlaces.size}/$placesAmount"
                progressAchievements.progress = percentage
                root.setOnClickListener { handleCategoryClicked(userCategory) }
            }
        }
    }

    interface CategoryClickListener {
        fun onCategoryClicked(userCategory: UserCategory)
    }
}