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
import com.glownia.maciej.exchangerates.utils.Constants.EXCHANGE_RATES_DATA_FRAGMENT_TAG
import com.glownia.maciej.exchangerates.utils.NetworkResult

class ExchangeRatesDataFragment : Fragment() {

    private var _binding: FragmentExchangeRatesBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()
    var listToDisplay = ArrayList<SingleRowDataPatternDto>()

    lateinit var exchangeRatesDataAdapter: ExchangeRatesDataAdapter

    var lastPosition = 0
    var countRequest = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentExchangeRatesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        mainViewModel.exchangeRatesDataResponse.observe(viewLifecycleOwner) { response ->
            showShimmerEffect()
            when (response) {
                is NetworkResult.Success -> {
                    Log.d(EXCHANGE_RATES_DATA_FRAGMENT_TAG, "onViewCreated: network success.")
                    hideShimmerEffect()
                    mainViewModel.exchangeRatesDataList.observe(viewLifecycleOwner) { listOfAllRecords ->
                        exchangeRatesDataAdapter.differ.submitList(listOfAllRecords)
                    }
                }
                is NetworkResult.Error -> {
                    Log.d(EXCHANGE_RATES_DATA_FRAGMENT_TAG, "onViewCreated: network error.")
                    hideShimmerEffect()
                    showErrorView()
                }
                is NetworkResult.Loading -> {
                    Log.d(EXCHANGE_RATES_DATA_FRAGMENT_TAG, "onViewCreated: network loading.")
                    showShimmerEffect()
                }
            }
        }
        exchangeRatesDataAdapter.setOnItemClickListener {
            if (it.name != "Dzień") {
                val action =
                    ExchangeRatesDataFragmentDirections.actionFirstFragmentToSecondFragment(it)
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(),
                    "Kliknij na wiersz z walutą.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        exchangeRatesDataAdapter = ExchangeRatesDataAdapter()
        binding.recyclerView.apply {
            adapter = exchangeRatesDataAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        Log.d("-----", "end")
                        Toast.makeText(requireContext(), "Last", Toast.LENGTH_LONG).show()
                        mainViewModel.getExchangeRatesData()
                        countRequest++
                        Toast.makeText(requireContext(),
                            "request number: $countRequest ",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    override fun onPause() {
        super.onPause()
        lastPosition =
            (binding.recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
    }

    override fun onResume() {
        super.onResume()
        (binding.recyclerView.layoutManager as LinearLayoutManager).scrollToPosition(lastPosition)
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        hideErrorView()
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        hideErrorView()
    }

    private fun hideErrorView() {
        binding.errorImageView.visibility = View.GONE
        binding.errorTextView.visibility = View.GONE
    }

    private fun showErrorView() {
        binding.recyclerView.visibility = View.GONE
        binding.errorImageView.visibility = View.VISIBLE
        binding.errorTextView.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}