package com.glownia.maciej.exchangerates.data

import android.os.Parcelable
import com.glownia.maciej.exchangerates.utils.Constants.DAY_WORD
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

/**
 * Data Transfer Object.
 * Will be used to display in a single row in a list in RecyclerView:
 *  - name : value
 * @param name is a [DAY_WORD] word or a currency symbol.
 * @param value is a date or a value of currency already calculated based on base currency.
 * @param base can be used to display base currency which from value is calculated.
 * @param date is a date single request come from.
 * JSON object.
 */
@Parcelize
data class RatesDto(
    val name: String = DAY_WORD,
    val value: String? = null,
    val base: String? = null,
    val date: String? = null,
) : Parcelable