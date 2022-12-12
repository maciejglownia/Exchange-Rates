package com.glownia.maciej.exchangerates.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glownia.maciej.exchangerates.data.SingleRowDataPatternDto
import com.glownia.maciej.exchangerates.databinding.SingleRowBinding

class ExchangeRatesDataAdapter(
    private val exchangeRatesDataList: List<SingleRowDataPatternDto>,
    private val onItemClicked: (SingleRowDataPatternDto) -> Unit,
) : RecyclerView.Adapter<ExchangeRatesDataAdapter.ExchangeRatesDataViewHolder>() {

    class ExchangeRatesDataViewHolder(
        private val binding: SingleRowBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exchangeRatesData: SingleRowDataPatternDto) {
            binding.nameTextView.text = exchangeRatesData.name
            binding.valueTextView.text = exchangeRatesData.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRatesDataViewHolder {
        val binding = SingleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExchangeRatesDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExchangeRatesDataViewHolder, position: Int) {
        val currentExchangeRatesData = exchangeRatesDataList[position]
        holder.itemView.setOnClickListener {
            onItemClicked(currentExchangeRatesData)
        }
        holder.bind(currentExchangeRatesData)
    }

    override fun getItemCount(): Int = exchangeRatesDataList.size
}