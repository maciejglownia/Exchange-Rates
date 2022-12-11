package com.glownia.maciej.exchangerates.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data Transfer Object.
 * Will be used to display in a single row in a list in RecyclerView:
 *  - name : value
 * @param name will be "Day" or currency symbol.
 * @param value will be date or value of currency already calculated based on base currency from
 * JSON object.
 */
@Parcelize
data class SingleRowDataPatternDto(
    val name: String,
    val value: String,
    val base: String,
    val date: String,
): Parcelable