package com.ducnd.demorealmmvp.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.ducnd.demorealmmvp.R
import com.ducnd.demorealmmvp.remote.model.ItemSong
import com.ducnd.realmmvp.ui.customview.GlideApp
import com.ducnd.realmmvp.ui.customview.ImageViewLocal

/**
 * Created by ducnd on 8/11/17.
 */

class SongAdapter(intf: ISongAdapter) : RecyclerView.Adapter<SongAdapter.Companion.ViewHolderSong>() {

    val mInterf: ISongAdapter = intf

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSong? {
        return ViewHolderSong(LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderSong, position: Int) {
        val item: ItemSong = mInterf.getData(position)
        holder.tvName.setText(item.title)
        holder.tvArtist.setText(item.artist)
        if (item.avatar != null && !item.avatar.equals("")) {
            mInterf.loadImage(item.avatar!!, holder.ivImg)
        } else {
            GlideApp.with(holder.ivImg)
                    .load(R.drawable.zing)
                    .placeholder(R.drawable.zing)
                    .error(R.drawable.zing)
                    .centerCrop()
                    .override(500)
                    .into(holder.ivImg)
        }

    }

    override fun getItemCount(): Int {
        return mInterf.getCount()
    }

    companion object {
        class ViewHolderSong(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvName: TextView = itemView.findViewById<View>(R.id.tv_name) as TextView
            val tvArtist: TextView = itemView.findViewById<View>(R.id.tv_artis) as TextView
            val ivImg: ImageViewLocal = itemView.findViewById<View>(R.id.iv_img) as ImageViewLocal
        }
    }

    interface ISongAdapter {
        fun getCount(): Int
        fun getData(position: Int): ItemSong
        fun loadImage(link: String, ivImg: ImageViewLocal)
    }
}
