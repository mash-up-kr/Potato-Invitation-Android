package com.mashup.patatoinvitation.presentation.searchlocation.view

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.BaseFragment
import com.mashup.patatoinvitation.databinding.FragmentSearchLocationBinding
import com.mashup.patatoinvitation.presentation.searchlocation.api.Documents
import com.mashup.patatoinvitation.presentation.searchlocation.viewmodel.LocationViewModel
import com.mashup.patatoinvitation.presentation.searchlocation.viewmodel.LocationViewModelFactory
import kotlinx.android.synthetic.main.fragment_search_location.*


class SearchLocationFragment :
    BaseFragment<FragmentSearchLocationBinding>(R.layout.fragment_search_location) {

    private val searchAddressAdapter: SearchLocationAdapter by lazy {
        SearchLocationAdapter { clickCallback(it) }

    }

    private val viewModel: LocationViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            LocationViewModelFactory()
        ).get(LocationViewModel::class.java)
    }

//    private val repository: InvitationRepository by lazy {
//        Injection.provideInvitationRepository()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = viewModel
        init()
    }

    private fun init() {
        initSearchView()
        initRecyclerview()
        searchViewListener()
    }

    private fun initSearchView() {
        svSearchLocation.apply {
            isIconified = false
            queryHint = getString(R.string.input_address_search_text)
        }
    }

    private fun initRecyclerview() {
        rvSearchLocation.apply {
            adapter = searchAddressAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun clickCallback(position: Int) {
        val data = searchAddressAdapter.getItem(position)
        viewModel.setClickItem(data)
    }

    private fun observableLocationData(keyword: String?) {
        keyword?.let {
            viewModel.getLocationList(it)
            viewModel.locationList.observe(requireActivity(), Observer { list ->
                addLocationList(list)
            })
        }
    }

    private fun addLocationList(locationList: List<Documents>) {
        searchAddressAdapter.setData(locationList)
    }

    private fun searchViewListener() {
        svSearchLocation.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                observableLocationData(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (svSearchLocation.query.isEmpty()) {
                    Toast.makeText(context, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
                    searchAddressAdapter.resetData()
                    viewModel.clearLocationList()
                }
                return false
            }
        })
    }

}
