package com.glownia.maciej.exchangerates.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.glownia.maciej.exchangerates.adapters.ExchangeRatesDataAdapter
import com.glownia.maciej.exchangerates.data.SingleRowDataPatternDto
import com.glownia.maciej.exchangerates.databinding.FragmentExchangeRatesBinding
import com.glownia.maciej.exchangerates.ui.viemodels.MainViewModel
import com.glownia.maciej.exchangerates.utils.Constants.EXCHANGE_RATES_FRAGMENT_TAG
import com.glownia.maciej.exchangerates.utils.NetworkResult

class ExchangeRatesDataFragment : Fragment() {

    private var _binding: FragmentExchangeRatesBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentExchangeRatesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.exchangeRatesDataResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mainViewModel.exchangeRatesDataList.observe(viewLifecycleOwner) {
                        Toast.makeText(
                            requireContext(),
                            "Success.",
                            Toast.LENGTH_SHORT
                        ).show()
                        setupRecyclerView(it)
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Error!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    Toast.makeText(
                        requireContext(),
                        "Loading...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupRecyclerView(it: List<SingleRowDataPatternDto>) {
        var previousTotal = 0
        var loading = true
        val visibleThreshold = 5
        var firstVisibleItem: Int
        var visibleItemCount: Int
        var totalItemCount: Int
        val exchangeRatesDataAdapter = ExchangeRatesDataAdapter(it) {
            if (it.name != "Dzień") {
                val action =
                    ExchangeRatesDataFragmentDirections.actionFirstFragmentToSecondFragment(it)
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(),
                    "Wybierz konkretną walutę z jej wartością aby poznać szczegóły.",
                    Toast.LENGTH_SHORT).show()
            }
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = exchangeRatesDataAdapter
            // https://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview/26561717#26561717
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    visibleItemCount = recyclerView.childCount
                    totalItemCount = (layoutManager as LinearLayoutManager).itemCount
                    firstVisibleItem =
                        (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false
                            previousTotal = totalItemCount
                        }
                    }
                    if (!loading && totalItemCount - visibleItemCount
                        <= firstVisibleItem + visibleThreshold
                    ) {
                        // End has been reached
                        Log.i(EXCHANGE_RATES_FRAGMENT_TAG, "Last row has been met.")
                        mainViewModel.getExchangeRates()
                        loading = true
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}