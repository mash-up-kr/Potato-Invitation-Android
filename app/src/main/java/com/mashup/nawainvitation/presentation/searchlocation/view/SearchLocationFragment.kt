package com.mashup.nawainvitation.presentation.searchlocation.view

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseFragment
import com.mashup.nawainvitation.databinding.FragmentSearchLocationBinding
import com.mashup.nawainvitation.presentation.main.MainViewModel
import com.mashup.nawainvitation.presentation.searchlocation.api.Documents
import com.mashup.nawainvitation.presentation.searchlocation.viewmodel.LocationViewModel
import com.mashup.nawainvitation.presentation.searchlocation.viewmodel.LocationViewModelFactory
import kotlinx.android.synthetic.main.fragment_search_location.*


class SearchLocationFragment :
    BaseFragment<FragmentSearchLocationBinding>(R.layout.fragment_search_location) {

    companion object {

        fun newInstance(): SearchLocationFragment {
            return SearchLocationFragment()
        }
    }

    private val searchAddressAdapter: SearchLocationAdapter by lazy {
        SearchLocationAdapter { clickCallback(it) }

    }

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())
            .get(MainViewModel::class.java)
    }

    private val locationViewModel: LocationViewModel by lazy {
        ViewModelProvider(
            this,
            LocationViewModelFactory()
        ).get(LocationViewModel::class.java)
    }

//    private val repository: InvitationRepository by lazy {
//        Injection.provideInvitationRepository()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = locationViewModel
        init()
    }

    private fun init() {
        initSearchView()
        initRecyclerview()
        searchViewListener()
        openInputLocation()
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
        locationViewModel.setClickItem(data)
    }

    private fun observableLocationData(keyword: String?) {
        keyword?.let {
            locationViewModel.getLocationList(it)
            locationViewModel.locationList.observe(requireActivity(), Observer { list ->
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
                    locationViewModel.clearLocationList()
                }
                return false
            }
        })
    }

    private fun openInputLocation() {
        locationViewModel.isItemSelected.observe(viewLifecycleOwner, Observer {
            //TODO [초희] inputLocation 으로 화면 전환 할 때 장소 데이터를 같이 넘겨 주어야 합니다.
            if (it) mainViewModel.listener.goToInvitationInputLocation()
        })
    }

}
