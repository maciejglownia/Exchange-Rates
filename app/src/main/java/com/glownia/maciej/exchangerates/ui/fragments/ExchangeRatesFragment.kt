package com.glownia.maciej.exchangerates.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.glownia.maciej.exchangerates.adapters.ExchangeRatesDataAdapter
import com.glownia.maciej.exchangerates.data.ExchangeRateData
import com.glownia.maciej.exchangerates.databinding.FragmentExchangeRatesBinding
import com.glownia.maciej.exchangerates.ui.viemodels.MainViewModel

class ExchangeRatesFragment : Fragment() {

    private val TAG = "ExchangeRatesDataFragment"
    private var _binding: FragmentExchangeRatesBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentExchangeRatesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exchangeRatesDataAdapter = ExchangeRatesDataAdapter()

        mainViewModel.exRatesData.observe(viewLifecycleOwner) { exRates ->
            val data = arrayListOf<ExchangeRateData>()
            data.add(exRates)
            exRates.let {
                exchangeRatesDataAdapter.submitList(data)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = exchangeRatesDataAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}