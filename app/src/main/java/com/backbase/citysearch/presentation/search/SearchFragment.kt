package com.backbase.citysearch.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.backbase.citysearch.databinding.FragmentSearchBinding
import com.backbase.citysearch.domain.entity.City
import com.backbase.citysearch.presentation.base.BaseFragment
import com.backbase.citysearch.presentation.navigation.MapNavigationCommand
import com.backbase.citysearch.presentation.search.adapter.CityAdapter
import com.backbase.citysearch.presentation.utils.DefaultSpacingItemDecorator
import com.backbase.citysearch.presentation.utils.dp
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment() {

    private val viewModel by viewModel<SearchViewModel>()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val cityAdapter by lazy {
        CityAdapter(listener = object : CityAdapter.CityClickListener {
            override fun onCityClick(obj: City) {
                viewModel.onCityClick(obj)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun initView() {
        super.initView()

        setupUI()
        observeEvents()
        observerLiveData()
    }

    private fun setupUI() {
        with(binding) {
            recycler.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = cityAdapter
                setHasFixedSize(true)
                addItemDecoration(DefaultSpacingItemDecorator(0.dp, 2.dp))
            }
            citySearchTv.addTextChangedListener(
                onTextChanged = { text: CharSequence?, _, _, _ ->
                    viewModel.proceedSearch(text?.toString().orEmpty())
                }
            )
        }
    }

    private fun observerLiveData() {
        viewModel.citySearchResult.subscribe { cityList ->
            cityAdapter.submitList(null)
            cityAdapter.submitList(cityList)
        }
    }

    private fun observeEvents() {
        viewModel.navEvent.subscribeToEvent { navCommand ->
            when (navCommand) {
                is MapNavigationCommand -> {
                    findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToMapFragment(navCommand.city))
                }
                else -> {
                    // Send logs?
                }
            }
        }
    }
}