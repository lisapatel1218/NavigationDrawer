package com.example.navigationdrawer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import com.google.android.gms.ads.AdSize


class google_ads : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_ads)

        val adsFrameLayout = findViewById<FrameLayout>(R.id.adsFramelayout)
//        AdSize.BANNER
        loadBannerAds(this,adsFrameLayout, AdSize.BANNER,R.string.banner_ads1)

//        AdSize.LARGE_BANNER
//        loadBannerAds(this,adsFrameLayout, AdSize.LARGE_BANNER,R.string.banner_ads1)
//
//        AdSize.FULL_BANNER
//        loadBannerAds(this,adsFrameLayout, AdSize.FULL_BANNER,R.string.banner_ads1)


        val showInterstitialAdsBtn = findViewById<Button>(R.id.showInterstitialAdsBtn)
        val myInterstitialAds = MyInterstitialAds(this)
        myInterstitialAds.loadInterstitialAds(R.string.interstitial_ads1)

        showInterstitialAdsBtn.setOnClickListener {
            myInterstitialAds.showInterstitialAds {

                val afterIntent = Intent(this,AfterInterstitialFinishedActivity::class.java)
                startActivity(afterIntent)

            }

        }

                val showSmallMediumNativeAdsBtn = findViewById<Button>(R.id.showSmallMediumNativeAdsBtn)
                showSmallMediumNativeAdsBtn.setOnClickListener {

                val smallMediumIntent = Intent(this,SmallMediumNativeAdsActivity::class.java)
                startActivity(smallMediumIntent)
                }
    }

}
