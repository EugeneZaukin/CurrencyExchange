package com.example.currencyexchange.ui.main

import android.os.Bundle
import androidx.fragment.app.*
import android.view.*
import com.example.currencyexchange.appComponent
import com.example.currencyexchange.databinding.FragmentMainBinding
import com.example.currencyexchange.ui.main.adapters.ValuteAdapter

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { ValuteAdapter(requireContext()) }

    private val viewModel by viewModels<MainViewModel> {
        requireContext().appComponent.mainViewModelFactory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvValutes.adapter = adapter

        viewModel.getValutes()
    }
}