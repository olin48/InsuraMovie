package com.panelic.insuramovie.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.panelic.insuramovie.R
import com.panelic.insuramovie.features.MainActivity
import com.panelic.insuramovie.model.Genres


class GenresAdapter(val context: Context): RecyclerView.Adapter<GenresAdapter.GenresHolder>() {

    private var lastCheckedPos = 0
    var genresList : List<Genres> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return GenresHolder(view)
    }

    override fun getItemCount(): Int {
        return genresList.size
    }

    override fun onBindViewHolder(holder: GenresHolder, position: Int) {
        if(position == lastCheckedPos) {
            holder.cardView.setCardBackgroundColor(Color.LTGRAY); //Define the re
        } else {
            holder.cardView.setCardBackgroundColor(Color.WHITE);
        }
        holder.tvName.text = genresList.get(position).name
        holder.cardView.setOnClickListener {
            val prevPos = lastCheckedPos
            lastCheckedPos = position
            notifyItemChanged(prevPos)
            notifyItemChanged(lastCheckedPos)

            val activity = context as MainActivity
            activity.dicoverByGenres(genresList.get(position).id.toString())
        }
    }

    fun setGenresListItems(blogList: List<Genres>){
        this.genresList = blogList;
        notifyDataSetChanged()
    }

    class GenresHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val tvName: TextView = itemView!!.findViewById(R.id.tv_cat_name)
        val cardView: CardView = itemView!!.findViewById(R.id.cv_genres)
    }

}