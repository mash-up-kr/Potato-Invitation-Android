package com.mashup.nawainvitation.presentation.invitationlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.nawainvitation.base.BaseViewModel
import com.mashup.nawainvitation.base.util.Dlog
import com.mashup.nawainvitation.data.base.BaseResponse
import com.mashup.nawainvitation.data.repository.InvitationRepository
import com.mashup.nawainvitation.presentation.invitationlist.adapter.InvitationListAdapter
import com.mashup.nawainvitation.presentation.invitationlist.model.InvitationListItem
import com.mashup.nawainvitation.presentation.main.model.InvitationsItem
import com.mashup.nawainvitation.presentation.main.model.TypeItem
import com.mashup.nawainvitation.presentation.main.model.mapToInvitationListItem


class InvitationListViewModel(
    private val invitationRepository: InvitationRepository,
    val listener: InvitationListListener
) : BaseViewModel() {

    private val _invitations = MutableLiveData<List<InvitationListItem>>()
    val invitations: LiveData<List<InvitationListItem>> get() = _invitations

    private val _showToast = MutableLiveData<String>()
    val showToast: LiveData<String> get() = _showToast

    private val _showEmptyView = MutableLiveData(true)
    val showEmptyView: LiveData<Boolean> get() = _showEmptyView


    init {
        getAllInvitations()
    }

    fun goMainActivity() {
        invitationRepository.getAllTypes(object : BaseResponse<List<TypeItem>> {
            override fun onSuccess(data: List<TypeItem>) {
                listener.goMainActivity(data)
            }

            override fun onFail(description: String) {
                Dlog.e("onFail $description")
                _showToast.postValue(description)
            }

            override fun onError(throwable: Throwable) {
                Dlog.e("onError ${throwable.message}")
                _showToast.postValue("error")
            }

            override fun onLoading() {
                listener.showLoading()
            }

            override fun onLoaded() {
                listener.hideLoading()
            }
        })
    }

    private fun getAllInvitations() {
        getInvitations { invitations ->
            Dlog.d("allItems : $invitations")

            val invitationListItem = mutableListOf<InvitationListItem>()
            invitations.forEach {
                invitationListItem.add(it.mapToInvitationListItem())
            }
            setTestData(invitationListItem)
        }
    }

    private fun setTestData(data: List<InvitationListItem>) {
        val invitations = data
            .sortedWith(
                compareBy(InvitationListItem::createdTime).thenByDescending(
                    InvitationListItem::invitationDateDefault
                )
            )
            .groupBy { it.createdYearMonth }

        // header list 생성
        val headerList = mutableListOf<InvitationListItem>()
        invitations.forEach { item ->
            headerList.add(
                InvitationListItem(
                    InvitationListAdapter.TYPE_HEADER,
                    createdTime = item.value[0].createdTime,
                    createdYearMonth = item.value[0].createdYearMonth
                ).getHeaderItem()
            )
        }
        // zip invitations, headers
        val result = mutableListOf<InvitationListItem>()
        invitations.map { it.value }.zip(headerList).forEach {
            result.add(it.second)
            result.addAll(it.first)
        }
        _invitations.value = result
    }

    private fun getInvitations(func: (List<InvitationsItem>) -> Unit) {
        invitationRepository.getInvitations()
            .subscribe({ items ->
                val invitations =
                    items.filter { it.hashcode.isNullOrEmpty().not() && it.templateId != -1 }
                if (invitations.isNullOrEmpty().not()) _showEmptyView.value = false
                func(invitations)
            }) {
                Dlog.e(it.message)
            }.addTo(compositeDisposable)
    }

    interface InvitationListListener {
        fun goMainActivity(data: List<TypeItem>)
        fun showLoading()
        fun hideLoading()
        fun showEmptyView()
        fun hideEmptyView()
    }

}