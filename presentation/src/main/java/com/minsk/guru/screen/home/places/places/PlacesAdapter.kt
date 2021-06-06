package com.minsk.guru.screen.home.places.places

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minsk.guru.databinding.ItemPlaceBinding
import com.minsk.guru.domain.model.Place
import kotlin.time.days

class PlacesAdapter(
    private val context: Context?,
    callback: Callback? = null
) : RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {

    var callback: Callback? = null
    private var items: List<Place> = listOf()

    init {
        this.callback = callback
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
        callback?.onPlaceClicked(place)
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
        }

        @SuppressLint("SetTextI18n")
        fun bind(place: Place) {
            binding.apply {
                tvPlaceName.text = place.name
                tvPlaceAddress.text = place.address
                tvPlaceCategory.text = place.category
                tvPlaceIsVisited.text = "Visited yet?"
            }
        }
    }

    interface Callback {
        fun onPlaceClicked(place: Place)
    }
}