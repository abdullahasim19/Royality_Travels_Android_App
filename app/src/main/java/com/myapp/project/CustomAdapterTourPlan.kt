package com.myapp.project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapterTourPlan(var plans:List<TourPlans>,context:Context):
    RecyclerView.Adapter<CustomAdapterTourPlan.ViewHolderTourPlan>()
{
    class ViewHolderTourPlan(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val id:TextView=itemView.findViewById<TextView>(R.id.tourplantripID)
        val location: TextView =itemView.findViewById<TextView>(R.id.tourplantripLocation)
        val dis:TextView=itemView.findViewById<TextView>(R.id.tourpladescription)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTourPlan {
        val v:View= LayoutInflater.from(parent.getContext()).inflate(R.layout.tourplans_layout,parent,false)
        return CustomAdapterTourPlan.ViewHolderTourPlan(v)
    }

    override fun onBindViewHolder(holder: ViewHolderTourPlan, position: Int) {
        holder.id.text=plans[position].idTrip.toString()

        holder.location.text=plans[position].locationTrip
        holder.dis.text=plans[position].plan.toString()
    }

    override fun getItemCount(): Int {
        return plans.size
    }


}
