package com.nkuppan.weatherapp.presentation.placesearch

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nkuppan.weatherapp.R
import com.nkuppan.weatherapp.core.extention.autoCleared
import com.nkuppan.weatherapp.core.extention.clearFocusAndHideKeyboard
import com.nkuppan.weatherapp.core.extention.showSnackBarMessage
import com.nkuppan.weatherapp.core.ui.fragment.BaseFragment
import com.nkuppan.weatherapp.databinding.FragmentPlaceSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlaceSearchFragment : BaseFragment() {

    private var binding: FragmentPlaceSearchBinding by autoCleared()

    private val placeSearchViewModel: PlaceSearchViewModel by viewModels()

    private val placeListAdapter = PlaceListAdapter { type, city ->
        viewLifecycleOwner.lifecycleScope.launch {
            if (type == 1) {
                placeSearchViewModel.saveSelectedCity(city)
            } else if (type == 2) {
                placeSearchViewModel.saveFavoriteCity(city)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaceSearchBinding.inflate(inflater, container, false)
        binding.viewModel = placeSearchViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeSearchContainer()

        initializeRecyclerView()

        initializeObserver()
    }

    private fun initializeObserver() {

        placeSearchViewModel.places.observe(viewLifecycleOwner) {
            placeListAdapter.submitList(it)
        }

        placeSearchViewModel.placeSelected.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        placeSearchViewModel.searchFavorites()
    }

    private fun initializeSearchContainer() {
        binding.query.apply {
            setOnEditorActionListener { _, actionId, _ ->
                return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    handleSearchAction()
                    true
                } else {
                    false
                }
            }

            setOnKeyListener { _, _, event ->
                if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                    handleSearchAction()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initializeRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = placeListAdapter
    }

    private fun handleSearchAction() {
        if (placeSearchViewModel.processQuery()) {
            binding.query.clearFocusAndHideKeyboard()
        } else {
            binding.root.showSnackBarMessage(R.string.enter_valid_query_string)
        }
    }
}