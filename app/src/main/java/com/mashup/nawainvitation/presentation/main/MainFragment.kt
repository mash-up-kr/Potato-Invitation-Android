package com.mashup.nawainvitation.presentation.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseFragment
import com.mashup.nawainvitation.base.ext.toast
import com.mashup.nawainvitation.databinding.FragmentMainBinding
import com.mashup.nawainvitation.presentation.invitationpreview.InvitationPreviewActivity
import com.mashup.nawainvitation.presentation.main.adapter.TypePagerAdapter

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

    private val typePagerAdapter by lazy {
        TypePagerAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = mainViewModel
        binding.documents = null

        initViewPager()
        initButton()
        initObserver()

        mainViewModel.loadInvitations()
    }

    private fun initViewPager() {
        with(binding.vpMain) {
            adapter = typePagerAdapter

            TabLayoutMediator(binding.tlMain, binding.vpMain) { _, _ -> }.attach()

            typePagerAdapter.replaceAll(mainViewModel.typeItems)

            val currentIndex = typePagerAdapter.getIndexFromTemplateId(
                mainViewModel.currentTypeItem.value?.templateId ?: 0
            )

            setCurrentItem(currentIndex, false)

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    mainViewModel.setCurrentTypeIndex(position)
                }
            })

            goLeft()
            goRight()
        }
    }

    private fun initButton() {
        binding.tvSeePreview.setOnClickListener {
            mainViewModel.currentTypeItem.value?.let { item ->
                InvitationPreviewActivity.startPreviewActivity(
                    requireContext(), item
                )
            }
        }
    }

    private fun initObserver() {
        mainViewModel.showToast.observe(viewLifecycleOwner, Observer {
            requireContext().toast(it)
        })
    }

    private fun goLeft() {
        binding.ibLeftMain.setOnClickListener {
            val current = getItem(-1)
            when {
                current >= 0 -> setCurrentItem(current)
                current < 0 -> setCurrentItem(5)
            }
        }
    }

    private fun goRight() {
        binding.ibRightMain.setOnClickListener {
            val current = getItem(1)
            when {
                current < 6 -> setCurrentItem(current)
                current == 6 -> setCurrentItem(0)
            }
        }
    }

    private val getItem = { i: Int -> binding.vpMain.currentItem + i }
    private val setCurrentItem = { current: Int ->
        binding.vpMain.apply {
            currentItem = current
            adapter?.notifyDataSetChanged()
        }
    }

}