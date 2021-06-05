package com.minsk.guru.screen.home.achievements

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.minsk.guru.R
import com.minsk.guru.databinding.ItemAchievementBinding
import com.minsk.guru.domain.model.Achievement

class AdapterAchievementsList(
    private val context: Context?,
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
        ViewHolder(ItemAchievementBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: AdapterAchievementsList.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    private fun handleItemClicked(position: Int) {
        val achievement = items[position]
        callback?.onPurchaseClicked(achievement)
    }


    inner class ViewHolder(
        private val binding: ItemAchievementBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val progress: Double = Math.random()

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
        fun bind(achievement: Achievement) {
            if (achievement.count == 0) {
                achievement.count = 1
            }
            binding.apply {
                category.text = achievement.category
                cardStatistic.findViewById<TextView>(R.id.tv_percentage).text =
                    context?.getString(
                        R.string.achievements_percentage,
                        ((progress * 100.0).toInt())
                    )
                cardStatistic.findViewById<TextView>(R.id.tv_progress).text =
                    "${(progress * achievement.count).toInt()}/${achievement.count}"
                progressAchievements.progress = (progress * 100.0).toInt()
            }
        }
    }

    interface Callback {
        fun onPurchaseClicked(achievement: Achievement)
    }
}