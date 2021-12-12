package com.minsk.guru.screen.home.places.places

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.minsk.guru.R
import com.minsk.guru.databinding.ItemPlaceBinding
import com.minsk.guru.domain.model.Place
import com.minsk.guru.utils.differenceWith

class PlacesAdapter(
    private val context: Context?,
    private val placeClickListener: OnPlaceClickListener?,
    private val isVisitedCheckboxClickListener: OnIsVisitedCheckboxClickListener?,
) : RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {

    private var categoryPlaces: List<Place> = listOf()
    private var visitedPlaces: List<Place> = listOf()

    fun set(categoryPlaces: List<Place>, visitedPlaces: List<Place>) {
        this.categoryPlaces = categoryPlaces
        this.visitedPlaces = visitedPlaces
        notifyItemRangeChanged(0, itemCount)
    }

    fun setVisitedPlaces(visitedPlaces: List<Place>) {
        val updatedPlaces = this.visitedPlaces.differenceWith(visitedPlaces)
        this.visitedPlaces = visitedPlaces
        updatedPlaces.forEach { place ->
            notifyItemChanged(categoryPlaces.indexOf(categoryPlaces.find { place.id == it.id }))
        }
    }

    override fun getItemCount(): Int = categoryPlaces.size

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
        holder.binding.place = categoryPlaces[position]
        holder.bind(
            categoryPlaces[position],
            visitedPlaces.any { it.id == categoryPlaces[position].id }
        )
    }

    private fun handlePlaceClicked(position: Int) {
        placeClickListener?.onPlaceClicked(categoryPlaces[position])
    }

    private fun handlePlaceIsVisitedCheckboxClicked(position: Int, isChecked: Boolean) {
        isVisitedCheckboxClickListener?.onIsVisitedCheckboxClicked(
            categoryPlaces[position],
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

        fun bind(place: Place, isVisited: Boolean) {
            binding.checkboxIsVisited.isChecked = isVisited
            Glide.with(context!!)
                .load(createYandexStaticMapsImageUrl(place.longitude, place.latitude, isVisited))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .error(R.drawable.ic_not_found)
                .into(binding.placePhoto)
        }

        private fun createYandexStaticMapsImageUrl(lon: Double, lat: Double, isVisited: Boolean) =
            "https://static-maps.yandex.ru/1.x/?lang=ru_RU&ll=$lon%2C$lat&size=600%2C300&z=17&l=map&pt=$lon%2C$lat%2Cpm2${if (isVisited) "gn" else "rd"}l"
    }

    interface OnPlaceClickListener {
        fun onPlaceClicked(place: Place)
    }

    interface OnIsVisitedCheckboxClickListener {
        fun onIsVisitedCheckboxClicked(place: Place, isVisited: Boolean)
    }
}