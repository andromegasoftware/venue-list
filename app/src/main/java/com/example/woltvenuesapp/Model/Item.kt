package com.example.woltvenuesapp.Model

data class Item(
    val description: String,
    val filtering: FilteringX,
    val image: Image,
    val link: Link,
    val overlay: String,
    val quantity: Int,
    val quantity_str: String,
    val sorting: Sorting,
    val template: String,
    val title: String,
    val track_id: String,
    val venue: Venue
)