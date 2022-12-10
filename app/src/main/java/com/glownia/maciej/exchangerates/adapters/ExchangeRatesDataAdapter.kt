package com.glownia.maciej.exchangerates.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glownia.maciej.exchangerates.data.SingleRowDataPatternDto
import com.glownia.maciej.exchangerates.databinding.SingleRowBinding

class ExchangeRatesDataAdapter(
    private val exchangeRateDataList: List<SingleRowDataPatternDto>,
) : RecyclerView.Adapter<ExchangeRatesDataAdapter.ExchangeRatesDataViewHolder>() {

    class ExchangeRatesDataViewHolder(
        private val binding: SingleRowBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exchangeRateData: SingleRowDataPatternDto) {
            binding.nameTextView.text = exchangeRateData.name
            binding.valueTextView.text = exchangeRateData.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRatesDataViewHolder {
        val binding = SingleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExchangeRatesDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExchangeRatesDataViewHolder, position: Int) {
        val currentExchangeRatesData = exchangeRateDataList[position]
        holder.bind(currentExchangeRatesData)
    }

    override fun getItemCount(): Int = exchangeRateDataList.size
}