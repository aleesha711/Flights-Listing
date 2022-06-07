package com.aleesha.feature_aero.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aleesha.core.extension.showSnackbar
import com.aleesha.core.extension.visibleOrGone
import com.aleesha.domain.aero.data.model.AirlinesItem
import com.aleesha.feature_aero.R
import com.aleesha.feature_aero.databinding.FragmentListBinding
import com.aleesha.feature_aero.ui.DetailAirlineFragment.Companion.AIRLINE_NAME_REMOVED_FAVORITE
import com.aleesha.feature_aero.ui.adapter.HomeAdapter
import com.aleesha.feature_aero.viewmodel.AirlineViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AirlinesListFragment : Fragment(R.layout.fragment_list) {

    private val binding by viewBinding(FragmentListBinding::bind)
    private val airlineViewModel: AirlineViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle()
        setClickListeners()
        setupObservers()
        setupAdapter()
        updateRemovedItemFromList()
    }

    private fun setToolbarTitle() {
        binding.toolbar.title = getString(R.string.title_airline)
    }
    private fun setClickListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            airlineViewModel.fetchAirlines()
        }
    }
    private fun setupObservers() {
        airlineViewModel.airlines.observe(viewLifecycleOwner) { airlines ->
            binding.errorTextView.visibleOrGone(false)
            (binding.list.adapter as? HomeAdapter)?.apply {
                submitList(airlines)
            }
            setLoading(false)
        }

        airlineViewModel.isNetworkFailure.observe(viewLifecycleOwner) {
            binding.errorTextView.visibleOrGone(true)
            binding.errorTextView.text = getString(R.string.network_error)
            requireView().showSnackbar(getString(R.string.network_error))
            setLoading(false)
        }

        airlineViewModel.unknownError.observe(viewLifecycleOwner) { error ->
            binding.errorTextView.visibleOrGone(true)
            binding.errorTextView.text = getString(R.string.unknown_error)
            requireView().showSnackbar(getString(R.string.unknown_error))
            setLoading(false)
            print(error)
        }
    }

    private fun setupAdapter() {
        binding.list.run {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)

            adapter = HomeAdapter(context, onItemClick = {
                (binding.list.adapter as? HomeAdapter)?.apply {
                    navigateToDetailFragment(getCurrentItem(it))
                }
            }, onFavoriteClick = {
                    (binding.list.adapter as? HomeAdapter)?.apply {
                        val item = getCurrentItem(it)
                        if (item.isFavorite) {
                            airlineViewModel.removeFavoriteAirline(item.name)
                            item.isFavorite = false
                        } else {
                            airlineViewModel.markFavoriteAirline(item.name)
                            item.isFavorite = true
                        }
                        notifyItemChanged(it)
                    }
                })
        }
    }

    private fun updateRemovedItemFromList() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            AIRLINE_NAME_REMOVED_FAVORITE
        )?.observe(viewLifecycleOwner) {
            (binding.list.adapter as? HomeAdapter)?.apply {
                updateItem(it)
                findNavController().currentBackStackEntry?.savedStateHandle?.remove<String>(AIRLINE_NAME_REMOVED_FAVORITE)
            }
        }
    }

    private fun hideLoader() {
        binding.progressBar.visibleOrGone(false)
    }

    private fun navigateToDetailFragment(airlinesItem: AirlinesItem) {
        findNavController().navigate(
            R.id.action_listFragment_to_detailFragment,
            bundleOf(AIRLINE_NAME to airlinesItem.name, AIRLINE_WEBSITE to airlinesItem.site, AIRLINE_PHONE to airlinesItem.phone, AIRLINE_LOGO to airlinesItem.logoURL)
        )
    }

    private fun setLoading(isLoading: Boolean) {
        if (binding.swipeRefresh.isRefreshing) {
            binding.swipeRefresh.isRefreshing = false
        }
        hideLoader()
    }

    companion object {
        const val AIRLINE_NAME = "AIRLINE_NAME"
        const val AIRLINE_LOGO = "AIRLINE_LOGO"
        const val AIRLINE_WEBSITE = "AIRLINE_WEBSITE"
        const val AIRLINE_PHONE = "AIRLINE_PHONE"
    }
}
