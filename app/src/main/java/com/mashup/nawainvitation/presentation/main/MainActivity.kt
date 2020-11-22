package com.mashup.nawainvitation.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseActivity
import com.mashup.nawainvitation.base.util.Dlog
import com.mashup.nawainvitation.data.injection.Injection
import com.mashup.nawainvitation.databinding.ActivityMainBinding
import com.mashup.nawainvitation.presentation.dialog.LoadingDialog
import com.mashup.nawainvitation.presentation.imagepicker.ImagePickerFragment
import com.mashup.nawainvitation.presentation.invitationinfo.InvitationInfoFragment
import com.mashup.nawainvitation.presentation.invitationpreview.InvitationPreviewActivity
import com.mashup.nawainvitation.presentation.main.model.TypeItem
import com.mashup.nawainvitation.presentation.searchlocation.api.Documents
import com.mashup.nawainvitation.presentation.searchlocation.view.InputLocationFragment
import com.mashup.nawainvitation.presentation.searchlocation.view.SearchLocationFragment
import com.mashup.nawainvitation.presentation.selectdatatime.SelectingDateTimeFragment
import com.mashup.nawainvitation.presentation.tutorial.TutorialActivity
import com.mashup.nawainvitation.utils.AppUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    MainViewModel.MainListener {

    companion object {

        private const val PARAM_TYPE_ITEMS = "type_items"

        fun startMainActivity(context: Context, typeItems: List<TypeItem>) {
            context.startActivity(
                Intent(context, MainActivity::class.java).apply {
                    Dlog.d("startMainActivity typeItems : ${typeItems.toTypedArray()}")
                    putExtra(PARAM_TYPE_ITEMS, typeItems.toTypedArray())
                }
            )
        }
    }

    private val mainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory(
                Injection.provideInvitationRepository(), this
            )
        ).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.model = mainViewModel

        initTopBar()
        initFragment()
        setTypeItems()
    }

    private fun initTopBar() {
        btnMainBack.setOnClickListener {
            AppUtils.hideSoftKeyBoard(this)
            onBackPressed()
        }
    }

    private fun initFragment() {
        goToInvitationMain()
        mainViewModel.isFirstInvitation()
    }

    private fun replaceFragmentWithTitle(title: String, fragment: Fragment, tag: String? = null) {
        tvMainTopBardTitle.text = title

        supportFragmentManager.beginTransaction()
            .replace(R.id.flMainContainer, fragment, tag)
            .commit()
    }

    private fun setTypeItems() {
        val typeItems = intent?.getParcelableArrayExtra(PARAM_TYPE_ITEMS)

        if (typeItems != null && typeItems.isNotEmpty()) {
            mainViewModel.typeItems.clear()
            mainViewModel.typeItems.addAll((typeItems.toList() as List<TypeItem>))
        } else {
            error("typeItems is null or empty")
        }
    }

    override fun goToInvitationMain() {
        replaceFragmentWithTitle(
            getString(R.string.make_invitation),
            MainFragment.newInstance(),
            MainFragment.TAG_ID
        )
    }

    override fun goToInvitationInfo() {
        replaceFragmentWithTitle(
            getString(R.string.input_invitation),
            InvitationInfoFragment.newInstance()
        )
    }

    override fun goToInvitationDate() {
        replaceFragmentWithTitle(
            getString(R.string.input_date),
            SelectingDateTimeFragment.newInstance()
        )
    }

    override fun goToInvitationInputLocation(documents: Documents?) {
        replaceFragmentWithTitle(
            getString(R.string.input_location_title),
            InputLocationFragment.newInstance(documents)
        )
    }

    override fun goToInvitationSearchLocation(documents: Documents?) {
        replaceFragmentWithTitle(
            getString(R.string.input_location_title),
            SearchLocationFragment.newInstance(documents)
        )
    }

    override fun goToInvitationPhoto() {
        replaceFragmentWithTitle(
            getString(R.string.input_photo),
            ImagePickerFragment.newInstance()
        )
    }

    override fun goToPreview(hashCode: String) {
        InvitationPreviewActivity.startPreviewActivityForShare(
            this,
            hashCode
        )
    }

    override fun gotoTutorial() {
        TutorialActivity.startTutorialActivity(this)
    }

    private val loadingDialog by lazy {
        LoadingDialog(this)
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
}