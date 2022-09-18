package com.example.currencyexchange.ui.main.adapters

import android.content.Context
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchange.databinding.ValuteItemBinding
import com.example.currencyexchange.domain.model.ValuteItem

class ValuteAdapter(private val context: Context) : RecyclerView.Adapter<ValuteViewHolder>() {
    private var valutes: List<ValuteItem> = listOf()
    private var valuteListener: ((ValuteItem) -> Unit)? = null

    fun setValutesAndListener(list: List<ValuteItem>, listener: (ValuteItem) -> Unit) {
        valutes = list
        valuteListener = listener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValuteViewHolder {
        val binding = ValuteItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ValuteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ValuteViewHolder, position: Int) {
        val valuteItem = valutes[position]
        holder.bind(valuteItem, valuteListener)
    }

    override fun getItemCount(): Int = valutes.size
}