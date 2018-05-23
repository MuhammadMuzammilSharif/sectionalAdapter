package io.github.muhammadmuzammilsharif.utils;

import android.content.Context
import android.util.DisplayMetrics

/**
 * Created by M_Muzammil Sharif on 17-May-17.
 */

fun convertDpToPixel(dp: Float, context: Context): Int {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return (dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

fun convertPixelsToDp(px: Float, context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}