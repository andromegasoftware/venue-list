package com.example.woltvenuesapp.Model

data class Venue(
    val address: String,
    val badges: List<Badge>,
    val categories: List<Any>,
    val city: String,
    val country: String,
    val currency: String,
    val delivers: Boolean,
    val delivery_price: String,
    val delivery_price_highlight: Boolean,
    val delivery_price_int: Int,
    val estimate: Int,
    val estimate_range: String,
    val franchise: String,
    val id: String,
    val location: List<Double>,
    val name: String,
    val online: Boolean,
    val price_range: Int,
    val product_line: String,
    val promotions: List<Any>,
    val rating: Rating,
    val short_description: String,
    val show_wolt_plus: Boolean,
    val slug: String,
    val tags: List<String>
)