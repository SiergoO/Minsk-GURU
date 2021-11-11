package com.minsk.guru.screen.home.places.places

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.minsk.guru.R
import com.minsk.guru.databinding.ItemPlaceBinding
import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.model.UserCategory

class PlacesAdapter(
    private val context: Context?,
    private val userCategory: UserCategory,
    private val placeClickListener: OnPlaceClickListener?,
    private val isVisitedCheckboxClickListener: OnIsVisitedCheckboxClickListener?,
) : RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {

    override fun getItemCount(): Int = userCategory.categoryPlaces.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_place,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PlacesAdapter.ViewHolder, position: Int) {
        holder.binding.place = userCategory.categoryPlaces[position]
        holder.bind(userCategory.visitedPlaces.any { it.id == userCategory.categoryPlaces[position].id })
    }

    private fun handlePlaceClicked(position: Int) {
        placeClickListener?.onPlaceClicked(userCategory.categoryPlaces[position])
    }

    private fun handlePlaceIsVisitedCheckboxClicked(position: Int, isChecked: Boolean) {
        isVisitedCheckboxClickListener?.onIsVisitedCheckboxClicked(
            userCategory.categoryPlaces[position],
            isChecked
        )
    }

    inner class ViewHolder(
        val binding: ItemPlaceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                handlePlaceClicked(adapterPosition)
            }
            binding.checkboxIsVisited.setOnClickListener {
                handlePlaceIsVisitedCheckboxClicked(
                    adapterPosition,
                    binding.checkboxIsVisited.isChecked
                )
            }
        }

        fun bind(isVisited: Boolean) {
            binding.checkboxIsVisited.isChecked = isVisited
        }
    }

    interface OnPlaceClickListener {
        fun onPlaceClicked(place: Place)
    }

    interface OnIsVisitedCheckboxClickListener {
        fun onIsVisitedCheckboxClicked(place: Place, isVisited: Boolean)
    }
}