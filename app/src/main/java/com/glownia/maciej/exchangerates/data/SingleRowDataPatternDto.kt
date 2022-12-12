package com.glownia.maciej.exchangerates.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data Transfer Object.
 * Will be used to display in a single row in a list in RecyclerView:
 *  - name : value
 * @param name is a "Dzień" word or a currency symbol.
 * @param value is a date or a value of currency already calculated based on base currency.
 * @param base can be used to display base currency which from value is calculated.
 * @param date is a date single request come from.
 * JSON object.
 */
@Parcelize
data class SingleRowDataPatternDto(
    val name: String,
    val value: String,
    val base: String,
    val date: String,
): Parcelable