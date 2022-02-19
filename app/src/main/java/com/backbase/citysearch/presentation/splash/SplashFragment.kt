package com.backbase.citysearch.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.backbase.citysearch.databinding.FragmentSplashBinding
import com.backbase.citysearch.presentation.base.BaseFragment
import com.backbase.citysearch.presentation.navigation.SearchNavigationCommand
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment() {

    private val viewModel by viewModel<SplashViewModel>()

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        super.initView()
        observeEvents()
    }

    private fun observeEvents() {
        viewModel.navigation.subscribeToEvent { navCommand ->
            when (navCommand) {
                is SearchNavigationCommand -> {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSearchFragment())
                }
                else -> {
                    // send logs
                }
            }
        }
    }
}