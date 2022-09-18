package com.example.currencyexchange.ui.main.adapters

import android.content.Context
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchange.databinding.ValuteItemBinding
import com.example.currencyexchange.domain.model.ValuteItem

class ValuteAdapter(private val context: Context) : RecyclerView.Adapter<ValuteViewHolder>() {
    private var valutes: List<ValuteItem> = listOf()

    fun setValutes(list: List<ValuteItem>) {
        valutes = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValuteViewHolder {
        val binding = ValuteItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ValuteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ValuteViewHolder, position: Int) {
        val valuteItem = valutes[position]
        holder.bind(valuteItem)
    }

    override fun getItemCount(): Int = valutes.size
}