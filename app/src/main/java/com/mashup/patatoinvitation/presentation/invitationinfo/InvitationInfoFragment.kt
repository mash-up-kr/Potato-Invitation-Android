package com.mashup.patatoinvitation.presentation.invitationinfo

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.BaseFragment
import com.mashup.patatoinvitation.data.injection.Injection
import com.mashup.patatoinvitation.databinding.FragmentInvitationInfoBinding
import com.mashup.patatoinvitation.presentation.main.MainViewModel

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

    private val invitationTitleViewModel by lazy {
        ViewModelProvider(
            this, InvitationInfoViewModelFactory(
                Injection.provideInvitationRepository(),
                mainViewModel
            )
        ).get(InvitationInfoViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = invitationTitleViewModel
    }
}