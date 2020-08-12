package com.mashup.patatoinvitation.presentation.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.BaseFragment
import com.mashup.patatoinvitation.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    companion object {

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    private val mainViewModel by lazy {
        ViewModelProvider(requireActivity()).get((MainViewModel::class.java))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = mainViewModel
    }
}