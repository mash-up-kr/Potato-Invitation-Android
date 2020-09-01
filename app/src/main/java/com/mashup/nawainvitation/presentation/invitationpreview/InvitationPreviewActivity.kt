package com.mashup.nawainvitation.presentation.invitationpreview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseActivity
import com.mashup.nawainvitation.databinding.ActivityInvitationPreviewBinding
import com.mashup.nawainvitation.presentation.main.MainActivity
import com.mashup.nawainvitation.presentation.typechoice.data.TypeData
import kotlinx.android.synthetic.main.activity_invitation_preview.*

class InvitationPreviewActivity :
    BaseActivity<ActivityInvitationPreviewBinding>(R.layout.activity_invitation_preview) {

    companion object {

        private const val DEFAULT_URL = "http://danivelop.com/preview"

        private const val EXTRA_VIEW_TYPE = "view_type"

        private const val EXTRA_TYPE_DATA = "type_data"

        private const val EXTRA_SHARED_URL = "shared_url"

        fun startPreviewActivity(context: Context, typeData: TypeData) {
            context.startActivity(
                Intent(context, InvitationPreviewActivity::class.java).apply {
                    putExtra(EXTRA_VIEW_TYPE, ViewType.PREVIEW)
                    putExtra(EXTRA_TYPE_DATA, typeData)
                }
            )
        }

        fun startPreviewActivityForShare(context: Context, url: String = DEFAULT_URL) {
            context.startActivity(
                Intent(context, InvitationPreviewActivity::class.java).apply {
                    putExtra(EXTRA_VIEW_TYPE, ViewType.SHARE_VIEW)
                    putExtra(EXTRA_SHARED_URL, url)
                }
            )
        }
    }

    enum class ViewType {
        PREVIEW, SHARE_VIEW
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initButton()
        initWebview()

        when (getViewType()) {
            ViewType.PREVIEW -> {
                webviewInvitation.loadUrl(DEFAULT_URL)
            }
            ViewType.SHARE_VIEW -> {
                webviewInvitation.loadUrl(getSharedUrl())
            }
        }
    }

    private fun getSharedUrl() = intent?.getStringExtra(EXTRA_SHARED_URL) ?: DEFAULT_URL

    private fun getTypeData() = intent?.getParcelableExtra<TypeData>(EXTRA_TYPE_DATA)!!

    private fun getViewType() = intent?.getSerializableExtra(EXTRA_VIEW_TYPE) as ViewType

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

        btnInvitationPreview.setOnClickListener {
            when (getViewType()) {
                ViewType.PREVIEW -> {
                    MainActivity.startMainActivityWithData(this, getTypeData())

                }
                ViewType.SHARE_VIEW -> {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, getSharedUrl())

                    val chooser = Intent.createChooser(intent, "공유하기")
                    startActivity(chooser)
                }
            }
        }
    }

    private fun initView() {
        btnInvitationPreview.text = when (getViewType()) {
            ViewType.PREVIEW -> getString(R.string.make_invitation)
            ViewType.SHARE_VIEW -> getString(R.string.share)
        }
    }
}