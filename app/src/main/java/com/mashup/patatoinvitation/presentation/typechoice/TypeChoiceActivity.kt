package com.mashup.patatoinvitation.presentation.typechoice

import android.content.Intent
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
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun initView() {
        binding.vpType.apply {
            adapter = typePagerAdapter
            offscreenPageLimit = 3

            //TODO: dp값으로 수정 필요
            val margin = 180
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
                    typePagerAdapter.getTypeData(position)
                    //TODO: 탭 선택 후 로직 실행
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