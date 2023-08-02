package ru.netology.nmedia.dto

import java.math.RoundingMode
import java.text.DecimalFormat

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int = 1,
    val shares:Int = 10,
    val views:Int = 999999,
    val likedByMe: Boolean = false
)

fun roundingNumbers(number:Long): String {
    val decimalFormat = DecimalFormat("#.#")
    decimalFormat.roundingMode = RoundingMode.DOWN
    return when (number) {
        in 0..999 -> "$number"
        in 1000..9_999 -> "${decimalFormat.format(number.toFloat() / 1_000)}K"
        in 10_000..999_999 -> "${number / 1_000}K"
        in 1_000_000..9_999_999 -> "${decimalFormat.format(number.toFloat() / 1_000_000)}M"
        else -> "${number / 1_000_000}M"
    }
}