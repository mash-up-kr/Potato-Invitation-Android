package com.mashup.nawainvitation.presentation.typechoice

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseActivity
import com.mashup.nawainvitation.base.ext.toast
import com.mashup.nawainvitation.data.base.BaseResponse
import com.mashup.nawainvitation.data.injection.Injection
import com.mashup.nawainvitation.data.repository.InvitationRepository
import com.mashup.nawainvitation.databinding.ActivityTypeChoiceBinding
import com.mashup.nawainvitation.presentation.dialog.LoadingDialog
import com.mashup.nawainvitation.presentation.invitationpreview.InvitationPreviewActivity
import com.mashup.nawainvitation.presentation.main.MainActivity
import com.mashup.nawainvitation.presentation.typechoice.data.TypeData
import com.mashup.nawainvitation.utils.AppUtils
import kotlinx.android.synthetic.main.activity_type_choice.*

class TypeChoiceActivity : BaseActivity<ActivityTypeChoiceBinding>(R.layout.activity_type_choice) {
    private val MARGIN_SIDE_VIEW_PAGE_DP = 48

    private val invitationRepository: InvitationRepository by lazy {
        Injection.provideInvitationRepository()
    }

    lateinit var typePagerAdapter: TypePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        initComponent()
        initView()
        initButton()
        requestType()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initComponent() {
        typePagerAdapter = TypePagerAdapter(this, layoutInflater)

    }

    private fun initButton() {
        tvStartInvitation.setOnClickListener {
            item?.let { typeData ->
                if (typeData.isEditing) {
                    MainActivity.startMainActivityWithData(this, typeData.templateId)
                } else {
                    InvitationPreviewActivity.startPreviewActivity(this, typeData.templateId)
                }
            }
        }
    }

    private var item: TypeData? = null

    private fun initView() {
        binding.vpType.apply {
            adapter = typePagerAdapter
            offscreenPageLimit = 3

            setViewPageSideMargin(MARGIN_SIDE_VIEW_PAGE_DP)

            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    updateCurrentItem(typePagerAdapter.getTypeData(position))

                }
            })
        }

        tlDotIndicator.setupWithViewPager(vpType, true)
    }

    private fun ViewPager.setViewPageSideMargin(dpMargin: Int) {
        val margin = AppUtils.dpToPx(context, dpMargin)
        pageMargin = 0
        setPadding(margin, 0, margin, 0)
    }

    fun updateCurrentItem(data: TypeData) {
        item = data
        tvStartInvitation.text = if (item?.isEditing == true) {
            getString(R.string.modify_comment_type_choice)
        } else {
            getString(R.string.start_comment_type_choice)
        }
    }

    private fun requestType() {
        invitationRepository.getInvitationTypes(object : BaseResponse<List<TypeData>> {
            override fun onSuccess(data: List<TypeData>) {
                updateCurrentItem(data.first())
                typePagerAdapter.addAllTypeDataList(data)
            }

            override fun onFail(description: String) {
                toast(description)
            }

            override fun onError(throwable: Throwable) {
                throwable.message?.let {
                    toast(it)
                }
            }

            override fun onLoading() {
                showLoading()
            }

            override fun onLoaded() {
                hideLoading()
            }
        })
    }

    private val loadingDialog by lazy { LoadingDialog(this) }

    private fun showLoading() {
        loadingDialog.show()
    }

    private fun hideLoading() {
        loadingDialog.hide()
    }
}