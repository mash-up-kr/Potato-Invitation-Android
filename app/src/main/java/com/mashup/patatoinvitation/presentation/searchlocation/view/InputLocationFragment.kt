package com.mashup.patatoinvitation.presentation.searchlocation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.BaseFragment
import com.mashup.patatoinvitation.databinding.FragmentInputLocationBinding
import com.mashup.patatoinvitation.presentation.searchlocation.viewmodel.LocationViewModel
import com.mashup.patatoinvitation.presentation.searchlocation.viewmodel.LocationViewModelFactory


class InputLocationFragment :
    BaseFragment<FragmentInputLocationBinding>(R.layout.fragment_input_location) {

    private val viewModel: LocationViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            LocationViewModelFactory()
        ).get(LocationViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = viewModel
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
        viewModel.place.observe(requireActivity(), Observer { doc ->
            viewModel.isSubmit.observe(requireActivity(), Observer {
                if (it) {
                    Toast.makeText(requireActivity(), "$doc", Toast.LENGTH_SHORT).show()
                    // TODO : 서버 통신
                }
            })
        })
    }
}