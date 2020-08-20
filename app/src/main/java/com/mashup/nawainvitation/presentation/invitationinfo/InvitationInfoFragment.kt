package com.mashup.nawainvitation.presentation.invitationinfo

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseFragment
import com.mashup.nawainvitation.data.injection.Injection
import com.mashup.nawainvitation.databinding.FragmentInvitationInfoBinding
import com.mashup.nawainvitation.presentation.main.MainViewModel

class InvitationInfoFragment :
    BaseFragment<FragmentInvitationInfoBinding>(R.layout.fragment_invitation_info) {

    companion object {

        fun newInstance(): InvitationInfoFragment {
            return InvitationInfoFragment()
        }
    }

    private val mainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    private val invitationInfoViewModel by lazy {
        ViewModelProvider(
            this, InvitationInfoViewModelFactory(
                Injection.provideInvitationRepository(),
                mainViewModel
            )
        ).get(InvitationInfoViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainModel = mainViewModel
        binding.infoModel = invitationInfoViewModel
    }
}