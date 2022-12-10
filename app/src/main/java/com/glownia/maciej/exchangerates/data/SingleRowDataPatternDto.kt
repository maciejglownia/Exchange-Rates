package com.glownia.maciej.exchangerates.data

/**
 * Data Transfer Object.
 * Will be used to display in a single row in a list in RecyclerView:
 *  - name : value
 * @param name will be "Day" or currency symbol.
 * @param value will be date or value of currency already calculated based on base currency from
 * JSON object.
 */
data class SingleRowDataPatternDto(
    val name: String,
    val value: String,
)