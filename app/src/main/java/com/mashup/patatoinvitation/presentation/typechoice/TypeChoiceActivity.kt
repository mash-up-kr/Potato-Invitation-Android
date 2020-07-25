package com.mashup.patatoinvitation.presentation.typechoice

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.BaseActivity
import com.mashup.patatoinvitation.base.ext.toast
import com.mashup.patatoinvitation.data.base.BaseResponse
import com.mashup.patatoinvitation.data.injection.Injection
import com.mashup.patatoinvitation.data.repository.InvitationRepository
import com.mashup.patatoinvitation.databinding.ActivityTypeChoiceBinding
import com.mashup.patatoinvitation.presentation.main.MainActivity
import com.mashup.patatoinvitation.presentation.typechoice.data.TypeData
import com.mashup.patatoinvitation.utils.AppUtils
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_type_choice.*

class TypeChoiceActivity : BaseActivity<ActivityTypeChoiceBinding>(R.layout.activity_type_choice) {

    private val invitationRepository: InvitationRepository by lazy {
        Injection.provideInvitationRepository()
    }

    lateinit var typePagerAdapter: TypePagerAdapter

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        initComponent()
        initView()
        initButton()
        requestType()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
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
            item?.let {
                MainActivity.startMainActivityWithData(this, it)
            }
        }
    }

    private var item: TypeData? = null

    private fun initView() {
        binding.vpType.apply {
            adapter = typePagerAdapter
            offscreenPageLimit = 3

            //TODO 두한이 수정 부탁드려요
            val margin = AppUtils.dpToPx(context, 48)
            setPadding(margin, 0, margin, 0)
            pageMargin = margin / 2

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
                    item = typePagerAdapter.getTypeData(position)
                    tvStartInvitation.text = if (item?.isEditing == true) {
                        getString(R.string.start_comment_type_choice)
                    } else {
                        getString(R.string.modify_comment_type_choice)
                    }
                }
            })
        }

        tlDotIndicator.setupWithViewPager(vpType, true)
    }

    private fun requestType() {
        invitationRepository.getInvitationTypes("1111", object : BaseResponse<List<TypeData>> {
            override fun onSuccess(data: List<TypeData>) {
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

            }

            override fun onLoaded() {

            }
        })
    }


}