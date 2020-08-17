package com.mashup.nawainvitation.presentation.invitationpreview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseActivity
import com.mashup.nawainvitation.databinding.ActivityInvitationPreviewBinding
import kotlinx.android.synthetic.main.activity_invitation_preview.*

class InvitationPreviewActivity :
    BaseActivity<ActivityInvitationPreviewBinding>(R.layout.activity_invitation_preview) {

    companion object {

        private const val EXTRA_PREVIEW_IS_SHARE = "preview_is_share"

        fun startPreviewActivity(context: Context, isShare: Boolean = true) {
            context.startActivity(
                Intent(context, InvitationPreviewActivity::class.java).apply {
                    putExtra(EXTRA_PREVIEW_IS_SHARE, isShare)
                }
            )
        }
    }

    private val previewUrl = "https://www.naver.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initButton()
        initWebview()
        webviewInvitation.loadUrl(previewUrl)

    }

    private fun initWebview() {
        webviewInvitation.webViewClient = WebViewClient()
        webviewInvitation.webChromeClient = WebChromeClient()

        with(webviewInvitation.settings) {
            javaScriptEnabled = true
        }
    }

    private fun initButton() {
        btnPreviewBack.setOnClickListener {
            onBackPressed()
        }

        btnInvitationPreviewShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, previewUrl)

            val chooser = Intent.createChooser(intent, "공유하기")
            startActivity(chooser)
        }
    }

    private fun initView() {
        intent?.getBooleanExtra(EXTRA_PREVIEW_IS_SHARE, true)?.let { isShare ->
            if (isShare) {
                btnInvitationPreviewShare.visibility = View.VISIBLE
            } else {
                btnInvitationPreviewShare.visibility = View.GONE
            }
        }
    }
}