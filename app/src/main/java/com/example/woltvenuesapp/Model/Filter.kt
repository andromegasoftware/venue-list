package com.example.woltvenuesapp.Model

data class Filter(
    val id: String,
    val name: String,
    val type: String,
    val values: List<Value>
)