package com.mashup.nawainvitation.presentation.invitationlist

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mashup.nawainvitation.BuildConfig
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseActivity
import com.mashup.nawainvitation.base.ext.toast
import com.mashup.nawainvitation.data.injection.Injection
import com.mashup.nawainvitation.databinding.ActivityInvitationListBinding
import com.mashup.nawainvitation.presentation.dialog.LoadingDialog
import com.mashup.nawainvitation.presentation.invitationlist.adapter.InvitationListAdapter
import com.mashup.nawainvitation.presentation.invitationlist.adapter.InvitationListItemDecoration
import com.mashup.nawainvitation.presentation.invitationlist.viewmodel.InvitationListViewModel
import com.mashup.nawainvitation.presentation.invitationlist.viewmodel.InvitationListViewModelFactory
import com.mashup.nawainvitation.presentation.invitationpreview.InvitationPreviewActivity
import com.mashup.nawainvitation.presentation.main.MainActivity
import com.mashup.nawainvitation.presentation.main.model.TypeItem
import kotlinx.android.synthetic.main.activity_invitation_list.*

class InvitationListActivity :
    BaseActivity<ActivityInvitationListBinding>(R.layout.activity_invitation_list),
    InvitationListViewModel.InvitationListListener {

    private val invitationListVM by lazy {
        ViewModelProvider(
            this,
            InvitationListViewModelFactory(
                Injection.provideInvitationRepository(), this
            )
        ).get(InvitationListViewModel::class.java)
    }

    private lateinit var adapter: InvitationListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.model = invitationListVM
        setSupportActionBar(toolbar)
        initObserver()
    }

    private fun initObserver() {
        invitationListVM.invitations.observe(this, Observer { items ->
            rvInvitationList.also {
                it.layoutManager = StaggeredGridLayoutManager(
                    InvitationListAdapter.SPAN_SIZE,
                    StaggeredGridLayoutManager.VERTICAL
                )
                adapter = InvitationListAdapter(items, this::clickCallback)
                it.adapter = adapter

                if (it.itemDecorationCount == 0) {
                    it.addItemDecoration(InvitationListItemDecoration(this))
                }
            }
        })

        invitationListVM.showToast.observe(this, Observer {
            this.toast(it)
        })

        invitationListVM.showEmptyView.observe(this, Observer {
            if (it) showEmptyView() else hideEmptyView()
        })
    }

    private fun clickCallback(position: Int) {
        val data = adapter.getItemWithPosition(position)
        InvitationPreviewActivity.startPreviewActivityForAgainView(this, data.hashcode)
    }

    private val loadingDialog by lazy {
        LoadingDialog(this)
    }

    override fun goMainActivity(data: List<TypeItem>) {
        MainActivity.startMainActivity(this@InvitationListActivity, data)
    }

    override fun showLoading() {
        if (loadingDialog.isShowing) return
        loadingDialog.show()
    }

    override fun hideLoading() {
        if (loadingDialog.isShowing) {
            loadingDialog.hide()
        }
    }

    override fun showEmptyView() {
        layoutListEmpty.visibility = View.VISIBLE
    }

    override fun hideEmptyView() {
        layoutListEmpty.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.appbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        LandingPageActivity.apply {
            return when (item.itemId) {
                R.id.action_version -> {
                    startLandingPageActivity(this@InvitationListActivity, EXTRA_VERSION_TYPE)
                    true
                }
                R.id.action_landing -> {
                    startLandingPageActivity(this@InvitationListActivity, EXTRA_LANDING_TYPE)
                    true
                }
                R.id.action_feedback -> {
                    sendEmail()
                    true
                }
                else -> return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun sendEmail() {
        val email = Intent(Intent.ACTION_SEND).apply {
            type = "plain/Text"
            val address = arrayOf("dlwls5201@gmail.com")
            putExtra(Intent.EXTRA_EMAIL, address)
            putExtra(Intent.EXTRA_SUBJECT, "<" + getString(R.string.app_name) + ">")
            putExtra(
                Intent.EXTRA_TEXT,
                "AppVersion :${BuildConfig.VERSION_NAME}\nDevice : ${Build.MODEL}\nAndroid OS : ${Build.VERSION.SDK_INT}\n\n Content :\n"
            )
        }
        startActivity(email)
    }
}