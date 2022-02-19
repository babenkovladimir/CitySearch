package com.backbase.citysearch.presentation.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.backbase.citysearch.domain.entity.City

object CityDiffCallback : DiffUtil.ItemCallback<City>() {

    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem == newItem
    }
}