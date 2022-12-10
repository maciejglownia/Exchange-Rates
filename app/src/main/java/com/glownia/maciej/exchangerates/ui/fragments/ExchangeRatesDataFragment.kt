package com.glownia.maciej.exchangerates.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.glownia.maciej.exchangerates.adapters.ExchangeRatesDataAdapter
import com.glownia.maciej.exchangerates.databinding.FragmentExchangeRatesBinding
import com.glownia.maciej.exchangerates.ui.viemodels.MainViewModel
import com.glownia.maciej.exchangerates.utils.Constants.EXCHANGE_RATES_FRAGMENT_TAG


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

        mainViewModel.exchangeRatesDataList.observe(viewLifecycleOwner) {
            var previousTotal = 0
            var loading = true
            val visibleThreshold = 5
            var firstVisibleItem: Int
            var visibleItemCount: Int
            var totalItemCount: Int
            val exchangeRatesDataAdapter = ExchangeRatesDataAdapter(it)
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = exchangeRatesDataAdapter
                // https://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview/26561717#26561717
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        visibleItemCount = recyclerView.childCount
                        Log.i(EXCHANGE_RATES_FRAGMENT_TAG, "creating creating")
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
                // I found some others solutions. But as for example below, after it "touches" the
                // bottom of recycler view it's does it automatically twice or more
//                var loading = true
//                var pastVisiblesItems: Int
//                var visibleItemCount: Int
//                var totalItemCount: Int

//                addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                        if (dy > 0) { //check for scroll down
//                            visibleItemCount = (layoutManager as LinearLayoutManager).childCount
//                            totalItemCount = (layoutManager as LinearLayoutManager).itemCount
//                            pastVisiblesItems = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
//                            if (loading) {
//                                if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
//                                    loading = false
//                                    Log.v("...", "Last Item Wow !")
//                                    // Do pagination.. i.e. fetch new data
//                                    mainViewModel.getExchangeRates()
//                                    loading = true
//                                }
//                            }
//                        }
//                    }
//                })
//
//                addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                        super.onScrollStateChanged(recyclerView, newState)
//                        if (!recyclerView.canScrollVertically(1)) {
//                            Toast.makeText(requireContext(),
//                                "Next previous day has been loaded.",
//                                Toast.LENGTH_SHORT).show()
//                            mainViewModel.getExchangesRates()
//                        }
//                    }
//                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}