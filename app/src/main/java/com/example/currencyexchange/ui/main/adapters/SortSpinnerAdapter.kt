package com.example.currencyexchange.ui.main.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.*
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.example.currencyexchange.databinding.SortSpinnerItemBinding

class SortSpinnerAdapter(context: Context, values: Array<String>) : ArrayAdapter<String>(context, 0, values) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = SortSpinnerItemBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.tvSortText.isVisible = false
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = SortSpinnerItemBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.tvSortText.text = getItem(position)
        return binding.root
    }
}