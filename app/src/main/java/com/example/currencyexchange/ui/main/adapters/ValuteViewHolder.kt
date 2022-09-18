package com.example.currencyexchange.ui.main.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchange.R
import com.example.currencyexchange.databinding.ValuteItemBinding
import com.example.currencyexchange.domain.model.ValuteItem

class ValuteViewHolder(valuteBinding: ValuteItemBinding) : RecyclerView.ViewHolder(valuteBinding.root) {
    private val binding = valuteBinding

    fun bind(valueItem: ValuteItem, listener: ((ValuteItem) -> Unit)?) {
        with(binding) {
            tvValuteName.text = root.resources.getString(
                R.string.full_name_valute_item,
                valueItem.charCode,
                valueItem.name
            )
            tvValuteValue.text = valueItem.value.toString()
            binding.ibFavouriteValute.setOnClickListener { listener?.invoke(valueItem) }
        }
        updateFavouriteIcon(valueItem.isFavourite)
    }

    private fun updateFavouriteIcon(isFavourite: Boolean) {
        with(binding.ibFavouriteValute) {
            if (isFavourite)
                setImageResource(android.R.drawable.btn_star_big_on)
            else
                setImageResource(android.R.drawable.btn_star_big_off)
        }
    }
}