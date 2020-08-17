package com.mashup.nawainvitation.presentation.searchlocation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseFragment
import com.mashup.nawainvitation.base.util.Dlog
import com.mashup.nawainvitation.databinding.FragmentInputLocationBinding
import com.mashup.nawainvitation.presentation.main.MainViewModel
import com.mashup.nawainvitation.presentation.searchlocation.api.Documents
import com.mashup.nawainvitation.presentation.searchlocation.viewmodel.InputLocationViewModel
import com.mashup.nawainvitation.presentation.searchlocation.viewmodel.InputLocationViewModelFactory

class InputLocationFragment :
    BaseFragment<FragmentInputLocationBinding>(R.layout.fragment_input_location),
    InputLocationViewModel.InputListener {

    companion object {
        const val PLACE_DATA = "place"
        fun newInstance(data: Documents?): InputLocationFragment {
            return InputLocationFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PLACE_DATA, data)
                }
            }
        }
    }

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())
            .get(MainViewModel::class.java)
    }

    private val inputLocationVM: InputLocationViewModel by lazy {
        ViewModelProvider(
            this,
            InputLocationViewModelFactory(this)
        ).get(InputLocationViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = inputLocationVM
        Dlog.d("${getDocuments()}")
    }

    private fun getDocuments() = arguments?.getParcelable(PLACE_DATA) as Documents?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    override fun goToSearch() {
        mainViewModel.listener.goToInvitationSearchLocation()
    }

    override fun submit() {
        inputLocationVM.place.observe(viewLifecycleOwner, Observer { doc ->
            Toast.makeText(requireActivity(), "$doc", Toast.LENGTH_SHORT).show()
        })
        mainViewModel.listener.goToInvitationMain()
    }

    private fun getData() {
        inputLocationVM.dataExists()
        val placeData = arguments?.getParcelable(PLACE_DATA) as Documents?
        placeData?.let { inputLocationVM.setLocation(it) }
    }
}