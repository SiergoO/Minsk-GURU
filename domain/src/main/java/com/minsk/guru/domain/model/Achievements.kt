package com.minsk.guru.domain.model

class Achievements {

    var achievements: MutableList<Achievement> = mutableListOf()

    constructor(): super()

    constructor(achievements: MutableList<Achievement>) {
        this.achievements = achievements
    }

    override fun toString(): String {
        return achievements.joinToString("\n") { it.name + " - " + it.description }
    }
}