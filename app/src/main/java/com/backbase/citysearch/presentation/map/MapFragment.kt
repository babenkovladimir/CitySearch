package com.backbase.citysearch.presentation.map

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.backbase.citysearch.R
import com.backbase.citysearch.domain.entity.City
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MapFragment : SupportMapFragment() {

    private val args: MapFragmentArgs by navArgs()

    private val viewModel by viewModel<MapViewModel> { parametersOf(args.city) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMapAsync { map ->
            viewModel.cityToShow.observe(viewLifecycleOwner) { city -> updateMap(map, city) }
        }
    }

    private fun updateMap(map: GoogleMap, city: City) {
        map.clear()
        map.uiSettings.setAllGesturesEnabled(true)

        val cityPosition = city.coordinates

        val title = getString(R.string.marker_title_holder, city.name, city.country)
        map.addMarker(MarkerOptions().position(cityPosition).title(title))
        map.moveCamera(CameraUpdateFactory.newLatLng(cityPosition))
    }
}