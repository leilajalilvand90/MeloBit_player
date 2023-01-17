package com.example.musicplayer.recyclerAdapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.musicplayer.R
import com.example.musicplayer.models.searchQuery.Song

class SongsAdapter(private val activity: Activity) : RecyclerView.Adapter<SongsAdapter.ViewHolder>() {



    private var songs: ArrayList<Song> = ArrayList()


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView3)
        val titleTextView: TextView = view.findViewById(R.id.textView5)
        val artistTextview: TextView = view.findViewById(R.id.textView7)
        val downloadCount: TextView = view.findViewById(R.id.textView8)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.song, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageView: ImageView = holder.imageView
        val titleTextView : TextView = holder.titleTextView
        val artistTextview : TextView = holder.artistTextview
        val downloadCount : TextView = holder.downloadCount


        titleTextView.text = songs[position].title
        artistTextview.text = songs[position].artists[0].fullName
        downloadCount.text = songs[position].downloadCount + " Downloads"


        Glide
            .with(activity)
            .load(songs[position].image.cover.url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    override fun getItemCount(): Int {
        return songs.size - 1
    }


    fun  setData(songs : ArrayList<Song>) {
        this.songs = songs
        activity.runOnUiThread {
            notifyDataSetChanged()
        }
    }


}