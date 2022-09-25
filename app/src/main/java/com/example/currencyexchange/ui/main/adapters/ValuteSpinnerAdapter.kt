package com.example.currencyexchange.ui.main.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.*
import android.widget.ArrayAdapter
import com.example.currencyexchange.R
import com.example.currencyexchange.databinding.SortSpinnerItemBinding
import com.example.currencyexchange.domain.model.ValuteItem

private const val EMPTY_TEXT = ""
private const val DEFAULT_NOMINAL = "1"

class ValuteSpinnerAdapter(context: Context, values: List<ValuteItem>) : ArrayAdapter<ValuteItem>(context, 0, values) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = SortSpinnerItemBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.tvSortText.text = getTextValute(getItem(position), false)
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = SortSpinnerItemBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.tvSortText.text = getTextValute(getItem(position), true)
        return binding.root
    }

    private fun getTextValute(valute: ValuteItem?, isDropDownView: Boolean): String {
        if (valute == null) return EMPTY_TEXT
        return context.resources.getString(
            R.string.full_name_valute_item,
            getDescriptionText(valute, isDropDownView),
            DEFAULT_NOMINAL
        )
    }

    private fun getDescriptionText(valute: ValuteItem, isDropDownView: Boolean): String =
        if (isDropDownView)
            context.resources.getString(R.string.full_name_valute_item, valute.charCode, valute.name)
        else
            valute.charCode
}