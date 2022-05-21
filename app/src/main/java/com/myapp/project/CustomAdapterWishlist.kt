package com.myapp.project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView

class CustomAdapterWishlist(var wish:List<WishListData>,var wishFull:List<WishListData>, var context: Context, var userInfo:User):
    RecyclerView.Adapter<CustomAdapterWishlist.ViewHolderWishlist>(),Filterable
{

    var filterType="ID"

    class ViewHolderWishlist(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val id=itemView.findViewById<TextView>(R.id.WishListtripID)
        val location=itemView.findViewById<TextView>(R.id.WishListtripLocation)
        val seats=itemView.findViewById<TextView>(R.id.WishListSeats)
        val startdate=itemView.findViewById<TextView>(R.id.WishListstarttripDate)
        val enddate=itemView.findViewById<TextView>(R.id.WishListendtripDate)
        val discounts=itemView.findViewById<TextView>(R.id.WishListtripDiscount)

        val price=itemView.findViewById<TextView>(R.id.WishListtripPrice)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderWishlist {
        val v:View= LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_view,parent,false)
        return CustomAdapterWishlist.ViewHolderWishlist(v)
    }

    override fun onBindViewHolder(holder: ViewHolderWishlist, position: Int) {
        holder.id.text=wish[position].tripdetailsWish.id.toString()
        holder.location.text=wish[position].tripdetailsWish.location
        holder.seats.text=wish[position].tripdetailsWish.seats.toString()
        holder.startdate.text=wish[position].tripdetailsWish.starttripDate
        holder.enddate.text=wish[position].tripdetailsWish.endtripDate
        holder.price.text=wish[position].tripdetailsWish.price.toString()
        val checkDiscount=wish[position].tripdetailsWish.discount
        if (checkDiscount<=0)
        {
            holder.discounts.text = "No Discount"
            holder.discounts.setTextColor(context.resources.getColor(R.color.design_default_color_error))
        }
        else {
            holder.discounts.text = (checkDiscount).toString()
            holder.discounts.setTextColor(context.resources.getColor(R.color.green))
        }
    }

    override fun getItemCount(): Int {
        return wish.size
    }

    fun setSearchType(type:String)
    {
        this.filterType=type
    }

    override fun getFilter(): Filter {
        return wishFilter
    }
    var wishFilter=object : Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var tempList=ArrayList<WishListData>()
            var filteredList:List<WishListData>
            if(constraint==null || constraint.length==0)
            {
                filteredList=wishFull.toList()
            }
            else
            {
                var pattern=constraint.toString().toLowerCase().trim()
                for (item:WishListData in wishFull)
                {
                    if(filterType=="ID") {
                        if (item.tripdetailsWish.id.toString().toLowerCase().contains(pattern)) {
                            tempList.add(item)
                        }
                    }
                    else if(filterType=="Location")
                    {
                        if (item.tripdetailsWish.location.toLowerCase().contains(pattern)) {
                            tempList.add(item)
                        }
                    }
                    else if(filterType=="Seats")
                    {
                        if (item.tripdetailsWish.seats.toString().toLowerCase().contains(pattern)) {
                            tempList.add(item)
                        }
                    }
                    else if(filterType=="Price")
                    {
                        if (item.tripdetailsWish.price.toString().toLowerCase().contains(pattern)) {
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
            var temps=results!!.values as List<WishListData>
            wish=temps.toList()
            notifyDataSetChanged()
        }

    }
}