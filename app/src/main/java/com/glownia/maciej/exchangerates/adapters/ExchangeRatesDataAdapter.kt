package com.glownia.maciej.exchangerates.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glownia.maciej.exchangerates.data.ExchangeRateData
import com.glownia.maciej.exchangerates.databinding.ExchangeRatesRowLayoutBinding

class ExchangeRatesDataAdapter(
) : ListAdapter<ExchangeRateData, ExchangeRatesDataAdapter.ExchangeRatesDataViewHolder>(
    DiffCallback) {

    inner class ExchangeRatesDataViewHolder(
        private var binding: ExchangeRatesRowLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.currencyNameTextView
            binding.currencyValueTextView
        }

        fun bind(exchangeRateData: ExchangeRateData) {
            binding.currencyNameTextView.text = "Day:"
            binding.currencyValueTextView.text = exchangeRateData.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRatesDataViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExchangeRatesDataViewHolder(
            ExchangeRatesRowLayoutBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ExchangeRatesDataViewHolder, position: Int) {
        val exRate = getItem(position)
        holder.bind(exRate)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ExchangeRateData>() {
            override fun areItemsTheSame(
                oldItem: ExchangeRateData,
                newItem: ExchangeRateData,
            ): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(
                oldItem: ExchangeRateData,
                newItem: ExchangeRateData,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}