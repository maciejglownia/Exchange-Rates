package com.glownia.maciej.exchangerates.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.glownia.maciej.exchangerates.data.SingleRowDataPatternDto
import com.glownia.maciej.exchangerates.databinding.SingleRowBinding

class ExchangeRatesDataAdapter() :
    RecyclerView.Adapter<ExchangeRatesDataAdapter.ExchangeRatesDataViewHolder>() {

    class ExchangeRatesDataViewHolder(
        val binding: SingleRowBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<SingleRowDataPatternDto>() {
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

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRatesDataViewHolder {
        val binding = SingleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("adapt", "onCreateViewHolder: invoked")
        return ExchangeRatesDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExchangeRatesDataViewHolder, position: Int) {
        val currentExchangeRatesData = differ.currentList[position]
        Log.d("adapt", "onBindViewHolder: invoked ${position.plus(1)}")
        holder.binding.nameTextView.text = currentExchangeRatesData.name
        holder.binding.valueTextView.text = currentExchangeRatesData.value
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(currentExchangeRatesData) }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener: ((SingleRowDataPatternDto) -> Unit)? = null

    fun setOnItemClickListener(listener: (SingleRowDataPatternDto) -> Unit) {
        onItemClickListener = listener
    }
}