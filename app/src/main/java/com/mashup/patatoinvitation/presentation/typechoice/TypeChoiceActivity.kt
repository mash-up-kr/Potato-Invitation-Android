package com.mashup.patatoinvitation.presentation.typechoice

import android.os.Bundle
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.BaseActivity
import com.mashup.patatoinvitation.databinding.ActivityTypeChoiceBinding
import com.mashup.patatoinvitation.presentation.typechoice.data.TypeData
import kotlinx.android.synthetic.main.activity_type_choice.*

class TypeChoiceActivity : BaseActivity<ActivityTypeChoiceBinding>(R.layout.activity_type_choice) {
    lateinit var typePagerAdapter: TypePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initComponent()

        initView()
    }

    private fun initComponent() {
        typePagerAdapter = TypePagerAdapter(layoutInflater)

        // test
        typePagerAdapter.apply {
            addTypeItem(
                TypeData(
                    "애교만점형",
                    "https://therubberduckdev.files.wordpress.com/2017/10/adcd5-recyclerview2bwith2bdividers.jpg",
                    "애교만점형 설명입니다"
                )
            )

            addTypeItem(
                TypeData(
                    "협박형",
                    "https://therubberduckdev.files.wordpress.com/2017/10/adcd5-recyclerview2bwith2bdividers.jpg",
                    "협박형 설명입니당"
                )
            )

            addTypeItem(
                TypeData(
                    "협박형2",
                    "https://therubberduckdev.files.wordpress.com/2017/10/adcd5-recyclerview2bwith2bdividers.jpg",
                    "협박형2 설명입니당"
                )
            )

            addTypeItem(
                TypeData(
                    "협박형3",
                    "https://therubberduckdev.files.wordpress.com/2017/10/adcd5-recyclerview2bwith2bdividers.jpg",
                    "협박형3 설명입니당"
                )
            )

            addTypeItem(
                TypeData(
                    "협박형4",
                    "https://therubberduckdev.files.wordpress.com/2017/10/adcd5-recyclerview2bwith2bdividers.jpg",
                    "협박형4 설명입니당"
                )
            )
        }
    }

    private fun initView() {
        binding.vpType.apply {
            adapter = typePagerAdapter
            offscreenPageLimit = 3

            //TODO: dp값으로 수정 필
            val margin = 180
            binding.vpType.setPadding(margin, 0, margin, 0)
            binding.vpType.pageMargin = margin / 2
        }

        tlDotIndicator.setupWithViewPager(vpType, true)
    }


}