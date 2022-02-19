package com.backbase.citysearch.presentation.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.backbase.citysearch.R
import com.backbase.citysearch.domain.entity.City

class CityAdapter(
    private val listener: CityClickListener
) : ListAdapter<City, CityAdapter.CityViewHolder>(AsyncDifferConfig.Builder(CityDiffCallback).build()) {


    interface CityClickListener {
        fun onCityClick(obj: City)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            listener = listener,
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CityViewHolder(
        private val listener: CityClickListener, view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(city: City) {
            with(itemView) {
                setOnClickListener { listener.onCityClick(city) }
                findViewById<AppCompatTextView>(R.id.titleTv).text = context.getString(R.string.title, city.name, city.country)
                findViewById<AppCompatTextView>(R.id.subtitleTv).text =
                    context.getString(R.string.subtitle, city.coordinates.latitude.toString(), city.coordinates.longitude.toString())
            }
        }
    }
}