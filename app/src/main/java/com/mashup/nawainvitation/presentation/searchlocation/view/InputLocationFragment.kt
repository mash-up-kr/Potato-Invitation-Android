package com.mashup.nawainvitation.presentation.searchlocation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseFragment
import com.mashup.nawainvitation.databinding.FragmentInputLocationBinding
import com.mashup.nawainvitation.presentation.main.MainViewModel
import com.mashup.nawainvitation.presentation.searchlocation.viewmodel.LocationViewModel
import com.mashup.nawainvitation.presentation.searchlocation.viewmodel.LocationViewModelFactory


class InputLocationFragment :
    BaseFragment<FragmentInputLocationBinding>(R.layout.fragment_input_location) {

    companion object {

        fun newInstance(): InputLocationFragment {
            return InputLocationFragment()
        }
    }

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())
            .get(MainViewModel::class.java)
    }

    private val locationViewModel: LocationViewModel by lazy {
        ViewModelProvider(
            this,
            LocationViewModelFactory()
        ).get(LocationViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = locationViewModel
        locationViewModel.initValue()

        openSearchLocation()
        goToMain()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        submitLocationData()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun submitLocationData() {
        locationViewModel.place.observe(requireActivity(), Observer { doc ->
            locationViewModel.isSubmit.observe(requireActivity(), Observer {
                if (it) {
                    Toast.makeText(requireActivity(), "$doc", Toast.LENGTH_SHORT).show()
                    // TODO : 서버 통신
                }
            })
        })
    }

    private fun openSearchLocation() {
        locationViewModel.isSearch.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) mainViewModel.listener.goToInvitationSearchLocation()
        })
    }


    private fun goToMain() {
        locationViewModel.isSubmit.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
            } // TODO: Main으로 화면 이동
        })
    }
}