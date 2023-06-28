package ru.netology.nmedia.dto

import java.math.RoundingMode
import java.text.DecimalFormat

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 99999,
    var shares:Int = 1099,
    var views:Int = 999999,
    var likedByMe: Boolean = false
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