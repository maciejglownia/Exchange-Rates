package com.glownia.maciej.exchangerates.adapters

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.glownia.maciej.exchangerates.data.RatesDto
import com.glownia.maciej.exchangerates.databinding.SingleRowBinding
import com.glownia.maciej.exchangerates.utils.Constants.DAY_WORD

class RatesAdapter() :
    RecyclerView.Adapter<RatesAdapter.ExchangeRatesDataViewHolder>() {

    class ExchangeRatesDataViewHolder(val binding: SingleRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<RatesDto>() {
        override fun areItemsTheSame(oldItem: RatesDto, newItem: RatesDto): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: RatesDto, newItem: RatesDto): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRatesDataViewHolder {
        val binding =
            SingleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExchangeRatesDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExchangeRatesDataViewHolder, position: Int) {
        val currentExchangeRatesData = differ.currentList[position]
        holder.binding.apply {
            // In row with date, date is set up to semicolon and value is empty purposely for better UI
            if (currentExchangeRatesData.name == DAY_WORD) {
                nameTextView.text = currentExchangeRatesData.name
                semicolonTextView.text = currentExchangeRatesData.date
                valueTextView.text = ""
                nameTextView.setTextColor(Color.BLACK)
                valueTextView.setTextColor(Color.BLACK)
                semicolonTextView.setTextColor(Color.BLACK)
                nameTextView.typeface = Typeface.DEFAULT_BOLD
                valueTextView.typeface = Typeface.DEFAULT_BOLD
                semicolonTextView.typeface = Typeface.DEFAULT_BOLD
            } else {

                nameTextView.text = currentExchangeRatesData.name
                semicolonTextView.text = ":"
                valueTextView.text = currentExchangeRatesData.value
                nameTextView.setTextColor(Color.GRAY)
                valueTextView.setTextColor(Color.GRAY)
                semicolonTextView.setTextColor(Color.GRAY)
                nameTextView.typeface = Typeface.DEFAULT
                valueTextView.typeface = Typeface.DEFAULT
                semicolonTextView.typeface = Typeface.DEFAULT
            }
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(currentExchangeRatesData) }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener: ((RatesDto) -> Unit)? = null

    fun setOnItemClickListener(listener: (RatesDto) -> Unit) {
        onItemClickListener = listener
    }
}