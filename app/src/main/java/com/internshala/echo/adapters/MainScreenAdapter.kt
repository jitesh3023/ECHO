@file:Suppress("UNREACHABLE_CODE")

package com.internshala.echo.adapters

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.internshala.echo.R
import com.internshala.echo.Songs
import com.internshala.echo.fragments.SongPlayingFragment


class MainScreenAdapter(_songDetails: ArrayList<Songs>, _context: Context) :
    RecyclerView.Adapter<MainScreenAdapter.MyViewHolder>() {
    var songDetails: ArrayList<Songs>? = null
    var mContext: Context? = null

    init {
        this.songDetails = _songDetails
        this.mContext = _context
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val songObject = songDetails?.get(position)
        holder.trackTitle.text = songObject?.songTitle.toString()
        holder.trackArtist.text = songObject?.artist.toString()
        holder.contentHolder?.setOnClickListener({
            val songPlayingFragment = SongPlayingFragment()
            val args = Bundle()

            args.putString("songArtist", songObject?.artist.toString())
            args.putString("songTitle", songObject?.songTitle.toString())
            args.putString("path", songObject?.songData.toString())
            args.putInt("songID", songObject?.songID?.toInt() as Int)
            args.putInt("songPosition", position)

            args.putParcelableArrayList("songData", songDetails)
            songPlayingFragment.arguments = args
            (mContext as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.details_fragment, songPlayingFragment)
                .addToBackStack("SongPlayingFragment")
                .commit()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_custom_mainscreen_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {

        if (songDetails == null) {
            return 0
        } else {
            return (songDetails as ArrayList<Songs>).size
        }
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var trackTitle = view.findViewById(R.id.trackTitle) as TextView
        var trackArtist = view.findViewById(R.id.trackArtist) as TextView
        var contentHolder: RelativeLayout? = null

        init {
            trackTitle = view.findViewById(R.id.trackTitle) as TextView
            trackArtist = view.findViewById(R.id.trackArtist) as TextView
            contentHolder = view.findViewById(R.id.contentRow) as RelativeLayout

        }
    }
}

