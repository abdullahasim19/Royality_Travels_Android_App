package com.myapp.project

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView

class CustomAdapterTripView(var trips:List<Trips>,var context:Context,var userInfo:User):
    RecyclerView.Adapter<CustomAdapterTripView.ViewHolderTrip>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTrip {
        val v:View= LayoutInflater.from(parent.getContext()).inflate(R.layout.trips_view,parent,false)
        return ViewHolderTrip(v)
    }

    override fun onBindViewHolder(holder: ViewHolderTrip, position: Int) {
        holder.id.text=trips[position].id.toString()

        holder.location.text=trips[position].location
        holder.seats.text=trips[position].seats.toString()
        holder.startdate.text=trips[position].starttripDate
        holder.enddate.text=trips[position].endtripDate
        holder.price.text=trips[position].price.toString()
        val checkDiscount=trips[position].discount
        if (checkDiscount<=0)
        {
            holder.discounts.text = "No Discount"
            holder.discounts.setTextColor(context.resources.getColor(R.color.design_default_color_error))
        }
        else {
            holder.discounts.text = checkDiscount.toString()
            holder.discounts.setTextColor(context.resources.getColor(R.color.green))
        }
        holder.booktrip.setOnClickListener {
            //Toast.makeText(context,"Book", Toast.LENGTH_SHORT).show()
            val inte =Intent(context,BookingActivity::class.java)
            inte.putExtra("trips",trips[position])
            inte.putExtra("UserInfo",userInfo)
            context.startActivity(inte)

        }
        holder.wishlist.setOnClickListener {
            Toast.makeText(context,"Wish", Toast.LENGTH_SHORT).show()
            val dataToInsert=WishListData(userInfo.email,trips[position])

            val refe=FirebaseHandler<WishListData>("Wishlist")
            refe.insert(dataToInsert)
            Toast.makeText(context,"Added to Wishlist",Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return trips.size
    }


    class ViewHolderTrip(ItemView: View):RecyclerView.ViewHolder(ItemView){
        val id=itemView.findViewById<TextView>(R.id.tripID)
        val location=itemView.findViewById<TextView>(R.id.tripLocation)
        val seats=itemView.findViewById<TextView>(R.id.tripSeats)
        val startdate=itemView.findViewById<TextView>(R.id.starttripDate)
        val enddate=itemView.findViewById<TextView>(R.id.endtripDate)
        val discounts=itemView.findViewById<TextView>(R.id.tripDiscount)
        val booktrip=itemView.findViewById<Button>(R.id.bookTrip)
        val wishlist=itemView.findViewById<Button>(R.id.wishlist)
        val price=itemView.findViewById<TextView>(R.id.tripPrice)
    }


}