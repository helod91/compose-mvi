package com.accenture.composemvi.data.model

data class Book(
    val id: String,
    val coverImageUrl: String,
    val title: String,
    val author: String,
    val genre: String,
    val price: Int,
    val description: String
)
