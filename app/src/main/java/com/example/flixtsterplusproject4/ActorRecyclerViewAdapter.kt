package com.example.flixtsterplusproject4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class ActorRecyclerViewAdapter(
    private val actors:  MutableList<Actor>,
    private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<ActorRecyclerViewAdapter.ActorViewHolder>() {

    inner class ActorViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: Actor? = null
        val mActorName: TextView = mView.findViewById<View>(R.id.nameView) as TextView
        val mActorHeadshot: ImageView = mView.findViewById<View>(R.id.headshotView) as ImageView

        override fun toString(): String {
            return mActorName.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_actor, parent, false)
        return ActorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val actor = actors[position]

        holder.mItem = actor
        holder.mActorName.text = actor.name

        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500/" + actor.headshotURL)
            .centerInside()
            .into(holder.mActorHeadshot)

        holder.mView.setOnClickListener {
            holder.mItem?.let { actor ->
                mListener?.onItemClick(actor)
            }
        }
    }

    override fun getItemCount(): Int {
        return actors.size
    }
}
