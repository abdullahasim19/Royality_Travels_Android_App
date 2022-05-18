package com.myapp.project

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapterExplore(var reviews:List<TripReview>,var c:Context):
    RecyclerView.Adapter<CustomAdapterExplore.ViewHolderExplore>()
{


    class ViewHolderExplore(ItemView: View): RecyclerView.ViewHolder(ItemView){
       val email=ItemView.findViewById<TextView>(R.id.exploreemail)
        val id =ItemView.findViewById<TextView>(R.id.exploretripid)
        val location=ItemView.findViewById<TextView>(R.id.exploretriplocation)
        val review=ItemView.findViewById<TextView>(R.id.exploretripreview)
        val rating=ItemView.findViewById<TextView>(R.id.exploretriprating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderExplore {
        val v:View= LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_trips,parent,false)

        return CustomAdapterExplore.ViewHolderExplore(v)
    }

    override fun onBindViewHolder(holder: ViewHolderExplore, position: Int) {
       holder.email.text=reviews[position].email
        holder.id.text=reviews[position].idofthetrip.toString()

        holder.location.text=reviews[position].locationofthetrip
        holder.review.text=reviews[position].reviewGiven
        holder.rating.text=reviews[position].ratingofTrip.toString()
    }

    override fun getItemCount(): Int {
        return reviews.size
    }
}