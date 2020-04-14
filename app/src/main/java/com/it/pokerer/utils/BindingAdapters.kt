package com.it.pokerer.utils

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.it.pokerer.R
import kotlin.math.absoluteValue
import kotlin.math.sign

@BindingAdapter("score")
fun TextView.bindScore(score: Int){
    text = score.absoluteValue.toString()
    setTextColor(
        ContextCompat.getColor(context,
            when(score.sign){
                -1 -> R.color.negative_score
                else -> R.color.positive_score
            }
        )
    )
}