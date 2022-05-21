package com.myapp.project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapterTourPlan(var plans:List<TourPlans>,var plansFull:List<TourPlans>,context:Context):
    RecyclerView.Adapter<CustomAdapterTourPlan.ViewHolderTourPlan>(),Filterable
{
    var filterType="ID"

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

    fun setSearchType(type:String)
    {
        this.filterType=type
    }

    override fun getFilter(): Filter {
        return tourplanFilter
    }
    var tourplanFilter=object : Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var tempList=ArrayList<TourPlans>()
            var filteredList:List<TourPlans>
            if(constraint==null || constraint.length==0)
            {
                filteredList=plansFull.toList()
            }
            else
            {
                var pattern=constraint.toString().toLowerCase().trim()
                for (item:TourPlans in plansFull)
                {
                    if(filterType=="ID") {
                        if (item.idTrip.toString().toLowerCase().contains(pattern)) {
                            tempList.add(item)
                        }
                    }
                    else if(filterType=="Location")
                    {
                        if (item.locationTrip.toLowerCase().contains(pattern)) {
                            tempList.add(item)
                        }
                    }
                    else if(filterType=="Plan")
                    {
                        if (item.plan.toString().toLowerCase().contains(pattern)) {
                            tempList.add(item)
                        }
                    }

                }
                filteredList=tempList.toList()
            }

            var results=FilterResults()
            results.values=filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            var temps=results!!.values as List<TourPlans>
            plans=temps.toList()
            notifyDataSetChanged()
        }

    }


}
