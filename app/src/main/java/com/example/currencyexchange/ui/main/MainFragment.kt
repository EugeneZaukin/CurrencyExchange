package com.example.currencyexchange.ui.main

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.*
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import com.example.currencyexchange.R
import com.example.currencyexchange.appComponent
import com.example.currencyexchange.databinding.FragmentMainBinding
import com.example.currencyexchange.domain.model.ValuteItem
import com.example.currencyexchange.ui.main.adapters.*
import com.google.android.material.button.MaterialButton

private const val ERROR_MESSAGE = "Error network"

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
        binding.rvLayout.rvValutes.adapter = adapter
        binding.rvLayout.rvValutes.itemAnimator = null
        initButtonsListener()
        initFlows()
    }

    private fun initButtonsListener() {
        with(binding) {
            btnPopular.setOnClickListener { viewModel.onClickPopular() }
            btnFavourites.setOnClickListener { viewModel.onClickFavourites() }
        }
    }

    private fun initFlows() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.btnPopularState.collect { isButtonChecked(it, binding.btnPopular) }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.btnFavouritesState.collect { isButtonChecked(it, binding.btnFavourites) }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.valutesState.collect(::addToRecyclerValutes)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loadingState.collect { binding.rvLayout.loadingValutes.isVisible = it }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.favouritesScreenState.collect { binding.rvLayout.tvFavouritesEmpty.isVisible = it }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.errorLoad.collect {
                Toast.makeText(requireContext(), ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isButtonChecked(isChecked: Boolean, view: MaterialButton) {
        val color = ContextCompat.getColor(requireContext(), R.color.teal_200)
        if (isChecked)
            view.strokeColor = ColorStateList.valueOf(color)
        else
            view.strokeColor = ColorStateList.valueOf(Color.BLACK)
    }

    private fun addToRecyclerValutes(list: List<ValuteItem>) {
        val diffUtilCallBack = DiffUtilCallBack(adapter.getValutes(), list)
        val calculateDiff = DiffUtil.calculateDiff(diffUtilCallBack)

        adapter.setValutesAndListener(list, viewModel::onValuteClick)
        calculateDiff.dispatchUpdatesTo(adapter)
    }
}