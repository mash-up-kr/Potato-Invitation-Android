package com.mashup.nawainvitation.presentation.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseFragment
import com.mashup.nawainvitation.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    companion object {

        const val TAG_ID = "MainFragment"

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

        //TODO 서버 통신을 통한 데이터 동기화
    }
}