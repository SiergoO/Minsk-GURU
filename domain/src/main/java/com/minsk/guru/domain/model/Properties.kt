package com.minsk.guru.domain.model

data class Properties(
    val CompanyMetaData: CompanyMetaData,
    val boundedBy: List<List<Double>>,
    val description: String,
    val name: String
)