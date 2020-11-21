package com.mashup.nawainvitation.presentation.invitationlist.model

import java.util.*


data class InvitationListItem(
    val viewType: Int,

    val templateId: Int? = null,
    val templateImage: Int? = null,
    val templateTypeName: String? = null,

    val invitationTitle: String? = null,
    val invitationDateDefault: Date? = null,
    val invitationDate: String? = null,
    val invitationTime: String? = null,
    val place: String? = null,

    val hashcode: String? = null,
    val createdTime: String? = null,
    val createdYearMonth: String? = null
) {
    fun getHeaderItem() = InvitationListItem(
        viewType, createdTime = createdTime, createdYearMonth = createdYearMonth
    )
}


