package com.mashup.patatoinvitation.presentation.typechoice

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.BaseActivity
import com.mashup.patatoinvitation.databinding.ActivityTypeChoiceBinding
import kotlinx.android.synthetic.main.activity_type_choice.*

class TypeChoiceActivity : BaseActivity<ActivityTypeChoiceBinding>(R.layout.activity_type_choice) {
    lateinit var typePagerAdapter: TypePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initComponent()

        initView()

        requestType()
    }

    private fun initComponent() {
        typePagerAdapter = TypePagerAdapter(this, layoutInflater)

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
        // TODO: 서버로 타입 요청
        // 리스트 응답 후
        // typePagerAdapter.addAllTypeDataList(List<TypeData>)
    }


}