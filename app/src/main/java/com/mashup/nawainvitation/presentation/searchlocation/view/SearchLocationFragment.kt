package com.mashup.nawainvitation.presentation.searchlocation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseFragment
import com.mashup.nawainvitation.databinding.FragmentSearchLocationBinding
import com.mashup.nawainvitation.presentation.main.MainViewModel
import com.mashup.nawainvitation.presentation.searchlocation.api.Documents
import com.mashup.nawainvitation.presentation.searchlocation.view.InputLocationFragment.Companion.PLACE_DATA
import com.mashup.nawainvitation.presentation.searchlocation.viewmodel.SearchLocationViewModel
import com.mashup.nawainvitation.presentation.searchlocation.viewmodel.SearchLocationViewModelFactory
import com.mashup.nawainvitation.utils.AppUtils
import kotlinx.android.synthetic.main.fragment_search_location.*


class SearchLocationFragment :
    BaseFragment<FragmentSearchLocationBinding>(R.layout.fragment_search_location),
    SearchLocationViewModel.SearchListener {

    private var data = Documents("", "", "")

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

    private val dispatcher by lazy { requireActivity().onBackPressedDispatcher }
    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            mainViewModel.listener.goToInvitationInputLocation(null)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dispatcher.addCallback(this, backPressedCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = searchLocationVM
        init()
    }

    private fun init() {
        initKeyboard()
        initRecyclerview()
        getData()
        searchViewListener()
    }

    private fun initKeyboard() {
        etSearchLocation.requestFocus()
        AppUtils.showSoftKeyBoard(requireActivity())
    }

    private fun initRecyclerview() {
        rvSearchLocation.apply {
            adapter = searchAddressAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun clickCallback(position: Int) {
        AppUtils.hideSoftKeyBoard(requireActivity())
        data = searchAddressAdapter.getItem(position)
        goToInput()
    }

    private fun loadData() {
        mainViewModel.invitations.observe(viewLifecycleOwner, Observer {
            it?.let {
                setEditText(it.invitationPlaceName)
                observableLocationData(it.invitationPlaceName)
            }
        })
    }

    private fun getData() {
        val placeData = arguments?.getParcelable(PLACE_DATA) as Documents?
        if (placeData != null) {
            setEditText(placeData.placeName)
            observableLocationData(placeData.placeName)
        }
        else loadData()
    }

    private fun observableLocationData(keyword: String?) {
        keyword?.let {
            searchLocationVM.getLocationList(it)
            searchLocationVM.locationList.observe(viewLifecycleOwner, Observer { list ->
                searchAddressAdapter.setData(list)
            })
        }
    }

    private fun searchViewListener() {
        etSearchLocation.doAfterTextChanged {
            if(it.toString().isBlank()) {
                searchLocationVM.clearLocationList(searchAddressAdapter.resetData())
                Toast.makeText(context, getString(R.string.search_location_plz_keyword), Toast.LENGTH_SHORT).show()
            } else observableLocationData(it.toString())
        }
    }

    private fun setEditText(keyword: String?) {
        etSearchLocation.setText(keyword)
        etSearchLocation.setSelection(etSearchLocation.length())
    }

    override fun goToInput() {
        mainViewModel.listener.goToInvitationInputLocation(data)
    }

    companion object {
        fun newInstance(documents: Documents?) : SearchLocationFragment {
            return SearchLocationFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PLACE_DATA, documents)
                }
            }
        }
    }
}
