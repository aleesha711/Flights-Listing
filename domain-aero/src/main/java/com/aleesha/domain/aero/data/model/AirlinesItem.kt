package com.aleesha.domain.aero.data.model

data class AirlinesItem(
    val __clazz: String,
    val alliance: String,
    val code: String,
    val defaultName: String,
    val logoURL: String,
    val name: String,
    val phone: String,
    val site: String,
    val usName: String,
    var isFavorite: Boolean
)
