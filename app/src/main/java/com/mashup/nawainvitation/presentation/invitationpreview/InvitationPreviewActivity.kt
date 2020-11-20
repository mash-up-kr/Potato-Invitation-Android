package com.mashup.nawainvitation.presentation.invitationpreview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseActivity
import com.mashup.nawainvitation.base.ext.longToast
import com.mashup.nawainvitation.base.util.Dlog
import com.mashup.nawainvitation.data.injection.Injection
import com.mashup.nawainvitation.databinding.ActivityInvitationPreviewBinding
import com.mashup.nawainvitation.presentation.main.model.TypeItem
import kotlinx.android.synthetic.main.activity_invitation_preview.*

class InvitationPreviewActivity :
    BaseActivity<ActivityInvitationPreviewBinding>(R.layout.activity_invitation_preview) {

    companion object {

        //TODO 광고 테스트 아이디
        private const val REWARD_ID = "ca-app-pub-3940256099942544/5224354917"

        private const val TAG = "AdMob"


        private const val DEFAULT_URL = "http://danivelop.com/"

        private const val INVITATION_PREVIEW_URL = "${DEFAULT_URL}preview"


        private const val EXTRA_VIEW_TYPE = "view_type"

        private const val EXTRA_TYPE_DATA = "type_data"

        private const val EXTRA_HASH_CODE = "hash_code"

        private const val EXTRA_ALREADY_VIEW = "already_view"


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

        fun startPreviewActivityForAgainView(context: Context, invitationHashCode: String?) {
            context.startActivity(
                Intent(context, InvitationPreviewActivity::class.java).apply {
                    putExtra(EXTRA_VIEW_TYPE, ViewType.SHARE_VIEW)
                    putExtra(EXTRA_VIEW_TYPE, ViewType.SHARE_VIEW)
                    putExtra(EXTRA_HASH_CODE, invitationHashCode)
                    putExtra(EXTRA_ALREADY_VIEW, true)
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

    private var alreadyShared = false

    private lateinit var rewardedAd: RewardedAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initButton()
        initWebview()
        initIntentData()

        rewardedAd = RewardedAd(this, REWARD_ID)
        when (getViewType()) {
            ViewType.PREVIEW -> {
                //..
            }
            ViewType.SHARE_VIEW -> {
                loadAd()
            }
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

                    if (alreadyShared) {
                        shareHashcode()
                        return@setOnClickListener
                    }

                    if (rewardedAd.isLoaded) {
                        rewardedAd.show(this, object : RewardedAdCallback() {
                            override fun onRewardedAdOpened() {
                                // Ad opened.
                                Log.d(TAG, "onRewardedAdOpened")
                            }

                            override fun onRewardedAdClosed() {
                                // Ad closed.
                                Log.d(TAG, "onRewardedAdClosed alreadyShared : $alreadyShared")

                                if (alreadyShared) {
                                    invitationRepository.updateInvitationHashcodeAndCreatedTime(
                                        hashCode = invitationHashCode,
                                        createdTime = System.currentTimeMillis() / 1000L
                                    )

                                    longToast("초대장이 생성되었습니다.")
                                    shareHashcode()

                                } else {
                                    createAndLoadRewardedAd()
                                }
                            }

                            override fun onUserEarnedReward(reward: RewardItem) {
                                // User earned reward.
                                Log.d(TAG, "reward type : ${reward.type}")
                                Log.d(TAG, "reward amount : ${reward.amount}")
                                alreadyShared = true
                            }

                            override fun onRewardedAdFailedToShow(adError: AdError) {
                                // Ad failed to display.
                                Log.d(TAG, "onRewardedAdFailedToShow : $adError")
                            }
                        })
                    } else {
                        Log.d(TAG, "The rewarded ad wasn't loaded yet.")
                    }
                }
            }
        }
    }

    private fun shareHashcode() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, getSharedLink())

        val chooser = Intent.createChooser(intent, getString(R.string.share))
        startActivity(chooser)
    }

    private fun initView() {
        btnInvitationPreview.text = when (getViewType()) {
            ViewType.PREVIEW -> getString(R.string.make_invitation)
            ViewType.SHARE_VIEW -> getString(R.string.share)
        }
    }

    private fun initIntentData() {
        alreadyShared = intent?.getBooleanExtra(EXTRA_ALREADY_VIEW, false) ?: false

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

    private fun loadAd() {
        if (alreadyShared) {
            return
        }

        showBtnProgress()

        rewardedAd.loadAd(AdRequest.Builder().build(), object : RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                Log.d(TAG, "onRewardedAdLoaded 1")
                hideBtnProgress()
            }

            override fun onRewardedAdFailedToLoad(adError: LoadAdError) {
                Log.e(TAG, adError.toString())
                hideBtnProgress()
            }
        })
    }

    private fun createAndLoadRewardedAd() {
        showBtnProgress()

        val rewardedAd = RewardedAd(this, REWARD_ID)
        val adLoadCallback = object : RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                // Ad successfully loaded.
                Log.d(TAG, "onRewardedAdLoaded 2")
                hideBtnProgress()
            }

            override fun onRewardedAdFailedToLoad(adError: LoadAdError) {
                // Ad failed to load.
                Log.e(TAG, adError.toString())
                hideBtnProgress()
            }
        }
        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)
        this.rewardedAd = rewardedAd
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

    private fun showBtnProgress() {
        flInvitationPreviewProgress.visibility = View.VISIBLE
    }

    private fun hideBtnProgress() {
        flInvitationPreviewProgress.visibility = View.GONE
    }
}