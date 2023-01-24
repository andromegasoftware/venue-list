package com.example.woltvenuesapp.Model

data class VenueModel(
    val created: Created,
    val expires_in_seconds: Int,
    val filtering: Filtering,
    val name: String,
    val page_title: String,
    val sections: List<Section>,
    val show_large_title: Boolean,
    val show_map: Boolean,
    val sorting: SortingX,
    val track_id: String
)