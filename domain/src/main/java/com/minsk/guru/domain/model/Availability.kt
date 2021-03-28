package com.minsk.guru.domain.model

data class Availability(
    val Intervals: List<Interval>?,
    val Monday: Boolean?,
    val Tuesday: Boolean?,
    val Wednesday: Boolean?,
    val Thursday: Boolean?,
    val Friday: Boolean?,
    val Saturday: Boolean?,
    val Sunday: Boolean?,
    val Everyday: Boolean?
)