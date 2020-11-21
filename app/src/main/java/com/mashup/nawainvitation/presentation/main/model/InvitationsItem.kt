package com.mashup.nawainvitation.presentation.main.model

import com.mashup.nawainvitation.data.room.entity.InvitationEntityV2
import com.mashup.nawainvitation.data.room.typeadpter.ImageListTypeAdapter
import com.mashup.nawainvitation.presentation.invitationlist.adapter.InvitationListAdapter
import com.mashup.nawainvitation.presentation.invitationlist.getTemplateIcon
import com.mashup.nawainvitation.presentation.invitationlist.model.InvitationListItem
import com.mashup.nawainvitation.utils.TimeUtils.getTimeStampToDate
import com.mashup.nawainvitation.utils.TimeUtils.toDateTimeDefault
import com.mashup.nawainvitation.utils.TimeUtils.toMonthDay
import com.mashup.nawainvitation.utils.TimeUtils.toTime
import com.mashup.nawainvitation.utils.TimeUtils.toYearMonth
import com.mashup.nawainvitation.utils.TimeUtils.toYearMonthDay

data class InvitationsItem(
    val templateId: Int?,
    val templateImageUrl: String?,
    val templateBackgroundImageUrl: String?,
    val templateTypeName: String?,
    val templateTypeDescription: String?,

    val invitationTitle: String?,
    val invitationContents: String?,
    val invitationTime: String?,

    val mapInfo: MapInfoItem?,

    val invitationImages: List<ImageInfoItem>?,

    val hashcode: String?,
    val createdTime: Long?
)

data class MapInfoItem(
    val invitationPlaceName: String?,
    val invitationAddressName: String?,
    val invitationRoadAddressName: String?,
    val longitude: Double?,
    val latitude: Double?
)

data class ImageInfoItem(
    val id: Long?,
    val imageUri: String?
)

fun InvitationEntityV2.mapToPresentation() = InvitationsItem(
    templateId = templateId,
    templateImageUrl = templateImageUrl,
    templateBackgroundImageUrl = templateBackgroundImageUrl,
    templateTypeName = templateTypeName,
    templateTypeDescription = templateTypeDescription,

    invitationTitle = invitationTitle,
    invitationContents = invitationContents,
    invitationTime = invitationTime,

    mapInfo = MapInfoItem(
        invitationPlaceName = locationEntity?.invitationPlaceName,
        invitationAddressName = locationEntity?.invitationAddressName,
        invitationRoadAddressName = locationEntity?.invitationRoadAddressName,
        longitude = locationEntity?.longitude,
        latitude = locationEntity?.latitude
    ),

    invitationImages = ImageListTypeAdapter.jsonStringToImageList(images),

    hashcode = hashCode,
    createdTime = createdTime
)

fun InvitationsItem.mapToInvitationListItem() = InvitationListItem(
    viewType = InvitationListAdapter.TYPE_INVITATION,
    templateId = templateId,
    templateImage = templateId.getTemplateIcon(),
    templateTypeName = templateTypeName,

    invitationTitle = invitationTitle,
    invitationDateDefault = invitationTime?.toDateTimeDefault(),
    invitationDate = invitationTime?.toMonthDay(),
    invitationTime = invitationTime?.toTime(),
    place = mapInfo?.invitationPlaceName,
    hashcode = hashcode,
    //포맷 바꿔서 사용
    createdTime = createdTime?.let { getTimeStampToDate(it).toYearMonthDay() },
    //toYearMonth() 사용
    createdYearMonth = createdTime?.let { getTimeStampToDate(it).toYearMonth() }
)

