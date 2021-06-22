package com.minsk.guru.data.repository.local.achievements

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.minsk.guru.data.repository.local.achievements.LocalAchievement.Companion.TABLE_NAME
import com.minsk.guru.domain.model.Achievement

@Entity(tableName = TABLE_NAME)
data class LocalAchievement(
    @PrimaryKey
    @ColumnInfo(name = ACHIEVEMENT_ID)
    val id: Int,
    @ColumnInfo(name = ACHIEVEMENT_NAME)
    val name: String? = null,
    @ColumnInfo(name = ACHIEVEMENT_DESCRIPTION)
    val description: String? = null,
    @ColumnInfo(name = ACHIEVEMENT_COUNT)
    val count: Int,
) {
    companion object {
        const val TABLE_NAME = "achievements"
        const val ACHIEVEMENT_ID = "achievement_id"
        const val ACHIEVEMENT_NAME = "achievement_name"
        const val ACHIEVEMENT_DESCRIPTION = "achievement_description"
        const val ACHIEVEMENT_COUNT = "achievement_count"
    }
}

fun LocalAchievement.toDomainAchievement() =
    Achievement(id, name ?: "", description ?: "", count)

fun Achievement.toLocalAchievement() = LocalAchievement(id, name, description, count)