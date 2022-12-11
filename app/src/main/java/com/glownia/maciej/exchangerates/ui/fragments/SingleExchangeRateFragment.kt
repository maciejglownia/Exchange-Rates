package com.glownia.maciej.exchangerates.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.glownia.maciej.exchangerates.databinding.FragmentSingleExchangeRateBinding
import com.glownia.maciej.exchangerates.ui.viemodels.MainViewModel

class SingleExchangeRateFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModel>()
    private val args: SingleExchangeRateFragmentArgs by navArgs()

    private var _binding: FragmentSingleExchangeRateBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSingleExchangeRateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argumentData = args.exchangeRatesDataArgument
        binding.apply {
            mainViewModel.currentDate.observe(viewLifecycleOwner) {
                val dateString = "Dzień: $it"
                dateTextView.text = dateString
            }
            valueTextView.text = argumentData.value
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}