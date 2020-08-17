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
import com.mashup.nawainvitation.base.util.Dlog
import com.mashup.nawainvitation.databinding.FragmentSearchLocationBinding
import com.mashup.nawainvitation.presentation.main.MainViewModel
import com.mashup.nawainvitation.presentation.searchlocation.api.Documents
import com.mashup.nawainvitation.presentation.searchlocation.viewmodel.SearchLocationViewModel
import com.mashup.nawainvitation.presentation.searchlocation.viewmodel.SearchLocationViewModelFactory
import kotlinx.android.synthetic.main.fragment_search_location.*


class SearchLocationFragment :
    BaseFragment<FragmentSearchLocationBinding>(R.layout.fragment_search_location),
    SearchLocationViewModel.SearchListener {

    private var data = Documents("", "", "")

    companion object {
        fun newInstance() =
            SearchLocationFragment()
    }

    private val searchAddressAdapter: SearchLocationAdapter by lazy {
        SearchLocationAdapter { clickCallback(it) }
    }

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())
            .get(MainViewModel::class.java)
    }

    private val searchLocationVM: SearchLocationViewModel by lazy {
        ViewModelProvider(
            this,
            SearchLocationViewModelFactory(this)
        ).get(SearchLocationViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = searchLocationVM
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
        Dlog.d("click")
        data = searchAddressAdapter.getItem(position)
        goToInput()
    }

    private fun observableLocationData(keyword: String?) {
        keyword?.let {
            searchLocationVM.getLocationList(it)
            searchLocationVM.locationList.observe(viewLifecycleOwner, Observer { list ->
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
                    searchLocationVM.clearLocationList()
                }
                return false
            }
        })
    }

    override fun goToInput() {
        mainViewModel.listener.goToInvitationInputLocation(data)
    }
}
