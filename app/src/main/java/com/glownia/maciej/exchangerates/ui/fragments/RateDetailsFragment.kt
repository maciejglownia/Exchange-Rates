package com.glownia.maciej.exchangerates.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.glownia.maciej.exchangerates.databinding.FragmentRateDetailsBinding
import com.glownia.maciej.exchangerates.ui.viemodels.MainViewModel

class RateDetailsFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModel>()
    private val args: RateDetailsFragmentArgs by navArgs()

    private var _binding: FragmentRateDetailsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRateDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rate = args.rate
        binding.apply {
            dateTextView.text = rate.date
            valueTextView.text = rate.value
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}