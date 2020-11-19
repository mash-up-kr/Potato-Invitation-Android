package com.mashup.nawainvitation.presentation.invitationpreview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseActivity
import com.mashup.nawainvitation.base.util.Dlog
import com.mashup.nawainvitation.data.injection.Injection
import com.mashup.nawainvitation.databinding.ActivityInvitationPreviewBinding
import com.mashup.nawainvitation.presentation.main.model.TypeItem
import kotlinx.android.synthetic.main.activity_invitation_preview.*

class InvitationPreviewActivity :
    BaseActivity<ActivityInvitationPreviewBinding>(R.layout.activity_invitation_preview) {

    companion object {

        private const val DEFAULT_URL = "http://danivelop.com/"

        private const val INVITATION_PREVIEW_URL = "${DEFAULT_URL}preview"

        private const val EXTRA_VIEW_TYPE = "view_type"

        private const val EXTRA_TYPE_DATA = "type_data"

        private const val EXTRA_HASH_CODE = "hash_code"

        fun startPreviewActivity(context: Context, typeItem: TypeItem) {
            context.startActivity(
                Intent(context, InvitationPreviewActivity::class.java).apply {
                    putExtra(EXTRA_VIEW_TYPE, ViewType.PREVIEW)
                    putExtra(EXTRA_TYPE_DATA, typeItem)
                }
            )
        }

        fun startPreviewActivityForShare(context: Context, invitationHashCode: String?) {
            context.startActivity(
                Intent(context, InvitationPreviewActivity::class.java).apply {
                    putExtra(EXTRA_VIEW_TYPE, ViewType.SHARE_VIEW)
                    putExtra(EXTRA_HASH_CODE, invitationHashCode)
                }
            )
        }
    }

    enum class ViewType {
        PREVIEW, SHARE_VIEW
    }

    private val invitationRepository by lazy {
        Injection.provideInvitationRepository()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initButton()
        initWebview()

        when (getViewType()) {
            ViewType.PREVIEW -> {
                val samplePreviewUrl = "${INVITATION_PREVIEW_URL}/${getTypeData().templateId}"
                Dlog.d("${getViewType()} -> url : $samplePreviewUrl")
                webviewInvitation.loadUrl(samplePreviewUrl)
            }
            ViewType.SHARE_VIEW -> {
                Dlog.d("${getViewType()} -> url : ${getSharedLink()}")
                webviewInvitation.loadUrl(getSharedLink())
            }
        }
    }

    private fun getTypeData() = intent?.getParcelableExtra<TypeItem>(EXTRA_TYPE_DATA)!!

    private fun getViewType() = intent?.getSerializableExtra(EXTRA_VIEW_TYPE) as ViewType

    private fun getSharedLink(): String {
        val invitationHashCode = intent?.getStringExtra(EXTRA_HASH_CODE)
        return if (invitationHashCode.isNullOrEmpty()) {
            INVITATION_PREVIEW_URL
        } else {
            "${DEFAULT_URL}${invitationHashCode}"
        }
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

        btnInvitationPreview.setOnClickListener {
            when (getViewType()) {
                ViewType.PREVIEW -> {
                    finish()
                }
                ViewType.SHARE_VIEW -> {

                    val invitationHashCode =
                        intent?.getStringExtra(EXTRA_HASH_CODE) ?: return@setOnClickListener

                    invitationRepository.updateInvitationHashcodeAndCreatedTime(
                        hashCode = invitationHashCode,
                        createdTime = System.currentTimeMillis() / 1000L
                    )

                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, getSharedLink())

                    val chooser = Intent.createChooser(intent, getString(R.string.share))
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