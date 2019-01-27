package org.nerdgrlapps.admobtutorial

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.android.gms.ads.*
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener

class MainActivity : AppCompatActivity() {

    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var mRewardedVideoAd: RewardedVideoAd

    private val mInterstitialAdListener = object : AdListener() {

        override fun onAdFailedToLoad(errorCode: Int) {
            Toast.makeText(
                this@MainActivity,
                "Interstitial ad failed to load: ${Utils.getUserFriendlyError(errorCode)}",
                Toast.LENGTH_LONG)
                .show()
        }

        override fun onAdClosed() {
            mInterstitialAd.loadAd(getAdRequest())
        }
    }

    private val mRewardedVideoAdListener = object : RewardedVideoAdListener {

        override fun onRewardedVideoAdLeftApplication() { }

        override fun onRewardedVideoAdLoaded() { }

        override fun onRewardedVideoAdOpened() { }

        override fun onRewardedVideoCompleted() { }

        override fun onRewardedVideoStarted() { }

        override fun onRewardedVideoAdClosed() {
            mRewardedVideoAd.loadAd(getString(R.string.rewarded_video_id), getAdRequest())
        }

        override fun onRewarded(reward: RewardItem?) {
            Toast.makeText(this@MainActivity, "Here is your reward!", Toast.LENGTH_SHORT).show()
        }

        override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {
            Toast.makeText(
                this@MainActivity,
                "Rewarded video ad failed to load: ${Utils.getUserFriendlyError(errorCode)}",
                Toast.LENGTH_LONG)
                .show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initializing mobile sdk
        MobileAds.initialize(this, getString(R.string.app_id))

        //setup banner ad
        val adView = findViewById<AdView>(R.id.banner_ad)
        adView.loadAd(getAdRequest())

        //setup fullscreen ad
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.interstitial_id)
        mInterstitialAd.loadAd(getAdRequest())
        mInterstitialAd.adListener = mInterstitialAdListener

        //setup rewarded video ad
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd.loadAd(getString(R.string.rewarded_video_id), getAdRequest())
        mRewardedVideoAd.rewardedVideoAdListener = mRewardedVideoAdListener
    }

    public fun onShowInterstitial(view: View) {
        if(mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        }
    }

    public fun onShowRewardedVideo(view: View) {
        if(mRewardedVideoAd.isLoaded) mRewardedVideoAd.show()
    }

    private fun getAdRequest(): AdRequest {
        return AdRequest.Builder().build()
    }
}
