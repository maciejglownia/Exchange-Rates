package com.glownia.maciej.exchangerates.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glownia.maciej.exchangerates.data.SingleRowDataPatternDto
import com.glownia.maciej.exchangerates.databinding.SingleRowBinding

class ExchangeRatesDataAdapter(
) : ListAdapter<SingleRowDataPatternDto, ExchangeRatesDataAdapter.ExchangeRatesDataViewHolder>(
    DiffCallback) {

    inner class ExchangeRatesDataViewHolder(
        private var binding: SingleRowBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.nameTextView
            binding.valueTextView
        }

        fun bind(exchangeRateData: SingleRowDataPatternDto) {
            binding.nameTextView.text = exchangeRateData.name
            binding.valueTextView.text = exchangeRateData.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRatesDataViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExchangeRatesDataViewHolder(
            SingleRowBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ExchangeRatesDataViewHolder, position: Int) {
        val exRate = getItem(position)
        holder.bind(exRate)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<SingleRowDataPatternDto>() {
            override fun areItemsTheSame(
                oldItem: SingleRowDataPatternDto,
                newItem: SingleRowDataPatternDto,
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: SingleRowDataPatternDto,
                newItem: SingleRowDataPatternDto,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}