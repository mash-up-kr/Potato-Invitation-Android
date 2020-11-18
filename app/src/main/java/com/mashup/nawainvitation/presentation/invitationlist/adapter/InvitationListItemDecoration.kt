package com.mashup.nawainvitation.presentation.invitationlist.adapter

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


class InvitationListItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val span25 = dpToPx(context, 25)
    private val span15 = dpToPx(context, 15)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        //상하 설정
        if (position == 0) {
            // 첫번 째 줄 아이템
            outRect.top = span25
            outRect.bottom = span25
        } else outRect.bottom = span25

        val lp =
            view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        val spanIndex = lp.spanIndex

        if (spanIndex == 0) {
            //왼쪽 아이템
            outRect.left = span25
            outRect.right = span15
        } else if (spanIndex == 1) {
            //오른쪽 아이템
            outRect.left = span15
            outRect.right = span25
        }
    }

    private fun dpToPx(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }
}