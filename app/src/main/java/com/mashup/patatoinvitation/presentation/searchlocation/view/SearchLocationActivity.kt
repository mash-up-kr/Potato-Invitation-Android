package com.mashup.patatoinvitation.presentation.searchlocation.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.BaseActivity
import com.mashup.patatoinvitation.databinding.ActivitySearchLocationBinding
import com.mashup.patatoinvitation.presentation.searchlocation.viewmodel.LocationViewModel
import com.mashup.patatoinvitation.presentation.searchlocation.viewmodel.LocationViewModelFactory

class SearchLocationActivity :
    BaseActivity<ActivitySearchLocationBinding>(R.layout.activity_search_location) {
    private lateinit var fm: FragmentManager
    private lateinit var searchLocationFragment: SearchLocationFragment
    private lateinit var inputLocationFragment: InputLocationFragment

    private val locationViewModel: LocationViewModel by lazy {
        ViewModelProvider(
            this,
            LocationViewModelFactory()
        ).get(LocationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.model = locationViewModel
        init()
    }

    private fun init() {
        initFragment()
        openSearchLocation()
        goToMain()
        openInputLocation()
    }

    private fun initFragment() {
        searchLocationFragment = SearchLocationFragment()
        inputLocationFragment = InputLocationFragment()
        openFragment(inputLocationFragment)
    }

    private fun openFragment(fragment: Fragment) {
        fm = supportFragmentManager
        fm.beginTransaction().run {
            replace(R.id.flSearchLocation, fragment)
            addToBackStack(null)
            commit()
        }
        locationViewModel.initValue()
    }

    private fun openSearchLocation() {
        locationViewModel.isSearch.observe(this, Observer<Boolean> {
            if (it) openFragment(searchLocationFragment)
        })
    }

    private fun goToMain() {
        locationViewModel.isSubmit.observe(this, Observer<Boolean> {
            if (it) {
            } // TODO: Main으로 화면 이동
        })
    }

    private fun openInputLocation() {
        locationViewModel.isItemSelected.observe(this, Observer {
            if (it) openFragment(inputLocationFragment)
        })
    }

    companion object {
        private const val EXTRA_TEMPLATE_ID = "template_id"

        fun startSearchLocationActivity(context: Context, templateId: Int) {
            context.startActivity(
                Intent(context, SearchLocationActivity::class.java).apply {
                    putExtra(EXTRA_TEMPLATE_ID, templateId)
                }
            )
        }
    }
}