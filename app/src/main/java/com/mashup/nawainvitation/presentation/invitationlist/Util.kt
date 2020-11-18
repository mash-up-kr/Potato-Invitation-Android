package com.mashup.nawainvitation.presentation.invitationlist

import com.mashup.nawainvitation.R

fun Int?.getTemplateIcon(): Int {
    return when (this) {
        1 -> R.drawable.ic_icon_main_money_40_px
        2 -> R.drawable.ic_icon_main_myway_40_px
        3 -> R.drawable.ic_icon_main_please_40_px
        4 -> R.drawable.ic_icon_main_threat_40_px
        5 -> R.drawable.ic_icon_main_love_40_px
        else -> R.drawable.ic_icon_main_elder_40_px
    }
}
