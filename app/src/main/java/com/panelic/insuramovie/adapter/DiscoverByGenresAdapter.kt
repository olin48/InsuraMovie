package com.panelic.insuramovie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.panelic.insuramovie.R
import com.panelic.insuramovie.features.MainActivity
import com.panelic.insuramovie.model.Discover

class DiscoverByGenresAdapter(val context: Context): RecyclerView.Adapter<DiscoverByGenresAdapter.DisByGenresHolder>() {

    var discoverList : List<Discover> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisByGenresHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_discover, parent, false)
        return DisByGenresHolder(view)
    }

    override fun getItemCount(): Int {
        return discoverList.size
    }

    override fun onBindViewHolder(holder: DisByGenresHolder, position: Int) {
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + discoverList.get(position).posterPath)
            .apply(RequestOptions().centerCrop())
            .into(holder.ivCover)

        holder.ivInfo.setOnClickListener{
            val activity = context as MainActivity
            activity.bottomSheetInfo(discoverList.get(position).posterPath.toString(),
                discoverList.get(position).title.toString(),
                discoverList.get(position).releaseDate.toString(),
                discoverList.get(position).overview.toString(),
                discoverList.get(position).voteAverage.toString(),
                discoverList.get(position).id.toString())
        }
    }

    fun setDisByGenresListItems(movieList: List<Discover>){
        this.discoverList = movieList;
        notifyDataSetChanged()
    }

    class DisByGenresHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val ivCover: ImageView = itemView!!.findViewById(R.id.iv_cover)
        val ivInfo: ImageView = itemView!!.findViewById(R.id.iv_info)
    }

}
