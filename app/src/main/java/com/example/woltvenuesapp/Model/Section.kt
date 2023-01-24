package com.example.woltvenuesapp.Model

data class Section(
    val items: List<Item>,
    val link: LinkX,
    val name: String,
    val template: String,
    val title: String
)