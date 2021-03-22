package com.minsk.guru.domain.model

data class Availability(
    val Friday: Boolean,
    val Intervals: List<Interval>,
    val Monday: Boolean,
    val Saturday: Boolean,
    val Sunday: Boolean,
    val Thursday: Boolean,
    val Tuesday: Boolean,
    val Wednesday: Boolean
)