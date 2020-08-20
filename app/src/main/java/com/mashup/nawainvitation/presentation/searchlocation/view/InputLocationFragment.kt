package com.mashup.nawainvitation.presentation.searchlocation.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseFragment
import com.mashup.nawainvitation.base.util.Dlog
import com.mashup.nawainvitation.data.base.BaseResponse
import com.mashup.nawainvitation.data.injection.Injection
import com.mashup.nawainvitation.data.repository.InvitationRepository
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

    private val invitationRepository: InvitationRepository by lazy {
        Injection.provideInvitationRepository()
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

    private val dispatcher by lazy { requireActivity().onBackPressedDispatcher }
    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            mainViewModel.listener.goToInvitationMain()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dispatcher.addCallback(this, backPressedCallback)
        getData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = inputLocationVM
        Dlog.d("${getDocuments()}")
    }

    private fun getDocuments() = arguments?.getParcelable(PLACE_DATA) as Documents?

    override fun goToSearch() {
        mainViewModel.listener.goToInvitationSearchLocation()
    }

    override fun submit() {
        inputLocationVM.place.observe(viewLifecycleOwner, Observer { doc ->
            invitationRepository.patchInvitationAddress(doc, mainViewModel.templateId, object : BaseResponse<Any>{
                override fun onSuccess(data: Any) {
                    mainViewModel.listener.goToInvitationMain()
                    Dlog.d("$data")
                }

                override fun onFail(description: String) {
                    Dlog.e(description)
                }

                override fun onError(throwable: Throwable) {
                    Dlog.e("$throwable")
                }

                override fun onLoading() {
                    mainViewModel.listener.showLoading()
                }

                override fun onLoaded() {
                    mainViewModel.listener.hideLoading()
                }
            })
        })
        mainViewModel.listener.goToInvitationMain()
    }

    private fun getData() {
        inputLocationVM.dataExists()
        val placeData = arguments?.getParcelable(PLACE_DATA) as Documents?
        placeData?.let { inputLocationVM.setLocation(it) }
    }
}