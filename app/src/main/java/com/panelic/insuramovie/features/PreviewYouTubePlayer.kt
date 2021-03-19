package com.panelic.insuramovie.features

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.panelic.insuramovie.R
import com.panelic.insuramovie.model.Discover
import com.panelic.insuramovie.model.DiscoverResult
import com.panelic.insuramovie.model.PreviewYoutubeResult
import com.panelic.insuramovie.model.YoutubeResult
import com.panelic.insuramovie.network.API_KEY
import com.panelic.insuramovie.network.YOUTUBE_KEY
import com.panelic.insuramovie.service.MovieAPI
import com.panelic.insuramovie.util.RetrofitSettings
import kotlinx.android.synthetic.main.activity_preview_youtube.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreviewYouTubePlayer : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_youtube)
        yt_pv.initialize(YOUTUBE_KEY, this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {
//        Toast.makeText(this, "Youtube Api Initialization Success", Toast.LENGTH_SHORT).show()
        if (!wasRestored) {
            val video_key:String = intent.getStringExtra("video_key").toString()
            player?.cueVideo(video_key);
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
//        Toast.makeText(this, "Youtube Api Initialization Failure", Toast.LENGTH_LONG).show()
    }
}