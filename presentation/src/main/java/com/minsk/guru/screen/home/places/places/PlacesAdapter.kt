package com.minsk.guru.screen.home.places.places

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minsk.guru.databinding.ItemPlaceBinding
import com.minsk.guru.domain.model.Place

class PlacesAdapter(
    private val context: Context?,
    placeClickListener: OnPlaceClickListener?,
    isVisitedCheckboxClickListener: OnIsVisitedCheckboxClickListener?,
) : RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {

    var placeClickListener: OnPlaceClickListener? = null
    var isVisitedCheckboxClickListener: OnIsVisitedCheckboxClickListener? = null
    private var items: List<Place> = listOf()

    init {
        this.placeClickListener = placeClickListener
        this.isVisitedCheckboxClickListener = isVisitedCheckboxClickListener
    }

    fun set(items: List<Place>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =
        items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemPlaceBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: PlacesAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    private fun handleItemClicked(position: Int) {
        val place = items[position]
        placeClickListener?.onPlaceClicked(place)
    }

    private fun handleItemIsVisitedCheckboxClicked(position: Int, isChecked: Boolean) {
        val place = items[position]
        isVisitedCheckboxClickListener?.onIsVisitedCheckboxClicked(place, isChecked)
    }

    inner class ViewHolder(
        private val binding: ItemPlaceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                adapterPosition.let { position ->
                    if (RecyclerView.NO_POSITION != position) {
                        handleItemClicked(position)
                    }
                }
            }
            binding.checkboxIsVisited.setOnClickListener {
                adapterPosition.let { position ->
                    if (RecyclerView.NO_POSITION != position) {
                        handleItemIsVisitedCheckboxClicked(position, binding.checkboxIsVisited.isChecked)
                    }
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(place: Place) {
            binding.apply {
                tvPlaceName.text = place.name
                tvPlaceAddress.text = place.address
                tvPlaceCategory.text = place.category
                checkboxIsVisited.text = "Visited yet?"
            }
        }
    }

    interface OnPlaceClickListener {
        fun onPlaceClicked(place: Place)
    }

    interface OnIsVisitedCheckboxClickListener {
        fun onIsVisitedCheckboxClicked(place: Place, isVisited: Boolean)
    }
}