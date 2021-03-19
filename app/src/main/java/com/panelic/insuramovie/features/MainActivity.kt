package com.panelic.insuramovie.features

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.panelic.insuramovie.R
import com.panelic.insuramovie.adapter.DiscoverByGenresAdapter
import com.panelic.insuramovie.adapter.GenresAdapter
import com.panelic.insuramovie.model.*
import com.panelic.insuramovie.network.API_KEY
import com.panelic.insuramovie.service.MovieAPI
import com.panelic.insuramovie.util.RetrofitSettings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var rvGenres: RecyclerView
    lateinit var genreAdapter: GenresAdapter
    lateinit var rvDiscoverByGenres: RecyclerView
    lateinit var disbygenreAdapter: DiscoverByGenresAdapter
    private lateinit var sheetInfoBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvGenres = findViewById(R.id.rv_category)
        genreAdapter = GenresAdapter(this)
        rvGenres.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        rvGenres.adapter = genreAdapter

        rvDiscoverByGenres = findViewById(R.id.rv_discover)
        disbygenreAdapter = DiscoverByGenresAdapter(this@MainActivity)
        rvDiscoverByGenres.layoutManager = GridLayoutManager(this@MainActivity, 2)
        rvDiscoverByGenres.adapter = disbygenreAdapter

        getGenres()

    }

    private fun getGenres() {
        val retro = RetrofitSettings().getRetrofitClientInstance().create(MovieAPI::class.java)
        retro.getGenres(API_KEY, "en-US").enqueue(object : Callback<GenresResult> {
            override fun onFailure(call: Call<GenresResult>, t: Throwable) {
                Log.e("onFailur", t.message.toString())
            }

            override fun onResponse(
                call: Call<GenresResult>,
                response: Response<GenresResult>
            ) {
                var result = response?.body()
                Log.d("onResponse", result.toString())

                var items = result?.genres
                if(response?.body() != null)
                    genreAdapter.setGenresListItems(items as List<Genres>)

                dicoverByGenres("28")
            }
        })
    }

    fun dicoverByGenres(id_genres: String) {
        val retro = RetrofitSettings().getRetrofitClientInstance().create(MovieAPI::class.java)
        retro.getDiscoverByGenres(API_KEY, id_genres).enqueue(object : Callback<DiscoverResult> {
            override fun onFailure(call: Call<DiscoverResult>, t: Throwable) {
                Log.e("onFailur", t.message.toString())
            }

            override fun onResponse(
                call: Call<DiscoverResult>,
                response: Response<DiscoverResult>
            ) {
                var result = response?.body()
                Log.d("onResponse", result.toString())

                var items = result?.results
                if(response?.body() != null)
                    disbygenreAdapter.setDisByGenresListItems(items as List<Discover>)
            }
        })
    }

    fun bottomSheetInfo(coverDtl: String, title: String, tahun: String, description: String, rating: String, id: String) {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_modal, null)
        val ivCoverDtl: ImageView = view!!.findViewById(R.id.iv_cover_dtl)
        val tvTitle: TextView = view!!.findViewById(R.id.tvTitle)
        val tvTahun: TextView = view!!.findViewById(R.id.tvTahun)
        val tvDescription: TextView = view!!.findViewById(R.id.tvDescription)
        val tvRating: TextView = view!!.findViewById(R.id.tvRating)
        val btnPreview: RelativeLayout = view!!.findViewById(R.id.btnPreview)

        Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + coverDtl)
            .apply(RequestOptions().centerCrop())
            .into(ivCoverDtl)

        val tahunRep = tahun.split("-").toTypedArray()
        tvTitle.text = title
        tvTahun.text = tahunRep[0]
        tvDescription.text = description
        tvRating.text = rating

        btnPreview.setOnClickListener {
            getVideoPreview(id)
        }

        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        dialog.show()
    }

    fun getVideoPreview(movie_id: String) {
        val retro = RetrofitSettings().getRetrofitClientInstance().create(MovieAPI::class.java)
        retro.getVideoPreview(movie_id, API_KEY).enqueue(object : Callback<PreviewYoutubeResult> {
            override fun onFailure(call: Call<PreviewYoutubeResult>, t: Throwable) {
                Log.e("onFailur", t.message.toString())
            }

            override fun onResponse(
                call: Call<PreviewYoutubeResult>,
                response: Response<PreviewYoutubeResult>
            ) {

                if (response.code() == 200) {
                    val resultResponse = response.body()!!
                    for (movie in resultResponse.results!!){
                        val intent = Intent(this@MainActivity, PreviewYouTubePlayer::class.java)
                        intent.putExtra("video_key", movie.key)
                        startActivity(intent)
                    }
                }
            }
        })
    }
}
