package com.example.masterunity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.masterunity.databinding.ActivityMainBinding
import com.unity3d.ads.IUnityAdsListener
import com.unity3d.ads.UnityAds
import com.unity3d.services.banners.BannerErrorInfo
import com.unity3d.services.banners.BannerView
import com.unity3d.services.banners.UnityBannerSize

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUnityAds()

        binding.btnBanner.setOnClickListener {
            initUnityBannerAds()
        }

        binding.btnInterstitial.setOnClickListener {
            showUnityInterstitialAds()
        }

        binding.btnRewarded.setOnClickListener {
            showUnityRewardedAds()
        }
    }

    fun initUnityAds(){
        UnityAds.initialize(applicationContext, "3761323", true)
        UnityAds.addListener(object : IUnityAdsListener {
            override fun onUnityAdsReady(placementId: String) {
                binding.txtLog.append("\n onUnityAdsReady $placementId")
            }
            override fun onUnityAdsStart(placementId: String) {
                binding.txtLog.append("\n onUnityAdsStart $placementId")
            }
            override fun onUnityAdsFinish(placementId: String, finishState: UnityAds.FinishState) {
                when (finishState) {
                    UnityAds.FinishState.COMPLETED -> {
                        binding.txtLog.append("\n onUnityAdsFinish $placementId COMPLETED")
                    }
                    UnityAds.FinishState.SKIPPED -> {
                        binding.txtLog.append("\n onUnityAdsFinish $placementId SKIPPED")
                    }
                    UnityAds.FinishState.ERROR -> {
                        binding.txtLog.append("\n onUnityAdsFinish $placementId ERROR")
                    }
                }
            }
            override fun onUnityAdsError(error: UnityAds.UnityAdsError, message: String) {
                binding.txtLog.append("\n onUnityAdsError $message")
            }
        })
    }

    fun initUnityBannerAds(){
        binding.txtLog.append("\n Unity banner init")
        binding.lyBannerAds.removeAllViews()
        val bannerView= BannerView(this,"banner", UnityBannerSize.getDynamicSize(this))
        bannerView.listener=object : BannerView.IListener{
            override fun onBannerLeftApplication(p0: BannerView?) {
                binding.txtLog.append("\n Unity onBannerLeftApplication ")}
            override fun onBannerClick(p0: BannerView?) {
                binding.txtLog.append("\n Unity onBannerClick ") }
            override fun onBannerLoaded(p0: BannerView?) {
                binding.txtLog.append("\n Unity onBannerLoaded ")
                binding.lyBannerAds.addView (p0)
            }
            override fun onBannerFailedToLoad(p0: BannerView?, p1: BannerErrorInfo?) {
                binding.txtLog.append("\n Unity onBannerFailedToLoad ")
            }
        }
        bannerView.load()
    }

    fun showUnityInterstitialAds(){
        if (UnityAds.isReady ("video")) {
            UnityAds.show (this,"video")
            binding.txtLog.append("\n Unity Interstitial ads Show")
        }else{
            binding.txtLog.append("\n Unity Interstitial ads not loaded")
        }
    }

    fun showUnityRewardedAds(){
        if (UnityAds.isReady ("rewardedVideo")) {
            UnityAds.show (this,"rewardedVideo")
            binding.txtLog.append("\n Unity rewardedVideo ads Show")
        }else{
            binding.txtLog.append("\n Unity rewardedVideo ads not loaded")
        }
    }
}