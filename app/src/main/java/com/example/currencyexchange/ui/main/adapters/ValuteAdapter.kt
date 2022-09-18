package com.example.currencyexchange.ui.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchange.databinding.ValuteItemBinding

class ValuteAdapter(private val context: Context) : RecyclerView.Adapter<ValuteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValuteViewHolder {
        val binding = ValuteItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ValuteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ValuteViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}