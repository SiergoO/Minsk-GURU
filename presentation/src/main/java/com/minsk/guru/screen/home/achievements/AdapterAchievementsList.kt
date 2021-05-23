package com.minsk.guru.screen.home.achievements

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.minsk.guru.R
import com.minsk.guru.domain.model.Achievement

class AdapterAchievementsList(
    context: Context?,
    callback: Callback? = null
) : RecyclerView.Adapter<AdapterAchievementsList.ViewHolder>() {

    var callback: Callback? = null
    private val layoutInflater = LayoutInflater.from(context)
    private var items: List<Achievement> = listOf()

    init {
        this.callback = callback
    }

    fun set(items: List<Achievement>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =
        items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(layoutInflater.inflate(R.layout.item_achievement, parent, false))


    override fun onBindViewHolder(holder: AdapterAchievementsList.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    private fun handleItemClicked(position: Int) {
        val achievement = items[position]
        callback?.onPurchaseClicked(achievement)
    }


    inner class ViewHolder(
        rootView: View
    ) : RecyclerView.ViewHolder(rootView) {

        private val title: TextView = rootView.findViewById(R.id.category)

        init {
            rootView.setOnClickListener {
                adapterPosition.let { position ->
                    if (RecyclerView.NO_POSITION != position) {
                        handleItemClicked(position)
                    }
                }
            }
        }

        fun bind(achievement: Achievement) {
            title.text = achievement.category
        }
    }

    interface Callback {
        fun onPurchaseClicked(achievement: Achievement)
    }
}