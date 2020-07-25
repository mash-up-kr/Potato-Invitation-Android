package com.mashup.patatoinvitation.presentation.searchaddress.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.ext.toast
import com.mashup.patatoinvitation.presentation.searchaddress.SearchAddressAdapter
import com.mashup.patatoinvitation.presentation.searchaddress.api.KakaoApiProvider
import com.mashup.patatoinvitation.presentation.searchaddress.model.Documents
import com.mashup.patatoinvitation.presentation.searchaddress.model.GetAddressResponse
import kotlinx.android.synthetic.main.activity_search_address.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: fragment로 수정
class SearchAddressActivity : AppCompatActivity() {

    private val searchAddressAdapter: SearchAddressAdapter by lazy {
        SearchAddressAdapter {
            clickCallback(
                it
            )
        }
    }
    private val itemList = mutableListOf<Documents>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_address)
        init()
    }

    private fun init() {
        initSearchView()
        searchViewListener()
        searchViewListener()
        initRecyclerview()
    }

    private fun initSearchView() {
        svSearchAddress.apply {
            isIconified = false
            queryHint = getString(R.string.input_address_search_text)
        }
    }

    private fun initRecyclerview() {
        rvSearchAddress.apply {
            adapter = searchAddressAdapter
            layoutManager = LinearLayoutManager(this@SearchAddressActivity)
        }
    }

    private fun clickCallback(position: Int) {
        val data = searchAddressAdapter.getItem(position)
        Intent(this, InputAddressActivity::class.java).apply {
            putExtra("place", data.placeName)
            //TODO: 건물이름, 도로명 주소, 좌표(x,y) 전달
            startActivity(this)
            finish()
        }
    }

    private fun searchViewListener() {
        svSearchAddress.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getSearchKeyword(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (svSearchAddress.query.isEmpty()) {
                    toast("검색어를 입력해주세요")
                    searchAddressAdapter.resetData()
                    itemList.clear()
                }
                return false
            }
        })
    }

    private fun getSearchKeyword(query: String?) {
        val getSearchKeyword = query?.let { KakaoApiProvider.getKakaoApi().getSearchKeyword(it) }
        getSearchKeyword?.enqueue(object : Callback<GetAddressResponse> {
            override fun onFailure(call: Call<GetAddressResponse>, t: Throwable) {
                Log.e("searchKeyword", t.toString())
            }

            override fun onResponse(
                call: Call<GetAddressResponse>,
                response: Response<GetAddressResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val keywordAddress = Documents("", "", query)
                        itemList.add(keywordAddress)
                        it.documents.forEach { doc -> itemList.add(doc) }
                        searchAddressAdapter.setData(itemList)
                    }
                } else {
                    Log.e("searchKeyword", response.message().toString())
                }
            }
        })
    }
}