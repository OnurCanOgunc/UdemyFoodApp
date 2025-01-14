package com.decode.udemyfoodapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExtendedIngredient(
    val amount: Double,
    val consistency: String,
    val image: String,
    val name: String,
    val original: String,
    val unit: String
): Parcelable
