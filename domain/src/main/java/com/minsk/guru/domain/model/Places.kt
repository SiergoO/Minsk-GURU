package com.minsk.guru.domain.model

data class Places(
    val places: List<Place>
) {
    override fun toString(): String {
        return places.joinToString(separator = "") {
            it.properties.PlaceMetaData?.name?.removePrefix(", ") + "\n" +
                    it.properties.PlaceMetaData?.address + "\n" +
                    ((it.properties.PlaceMetaData?.Phones?.first()?.formatted ?: "No Phones")) + "\n" +
                    ((it.properties.PlaceMetaData?.url?: "No URL") + "\n" + "\n")
        }
    }
}

