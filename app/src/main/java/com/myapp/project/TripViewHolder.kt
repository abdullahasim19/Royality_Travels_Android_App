package com.myapp.project

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TripViewHolder : RecyclerView.ViewHolder {
    lateinit var id:TextView
    lateinit var location:TextView
    lateinit var seats:TextView
    lateinit var startdate:TextView
    lateinit var enddate:TextView
    lateinit var discounts:TextView
    lateinit var booktrip:Button
    lateinit var wishlist:Button

    constructor(itemView:View) : super(itemView)
    {
        id=itemView.findViewById<TextView>(R.id.tripID)
        location=itemView.findViewById<TextView>(R.id.tripLocation)
        seats=itemView.findViewById<TextView>(R.id.tripSeats)
        startdate=itemView.findViewById<TextView>(R.id.starttripDate)
        enddate=itemView.findViewById<TextView>(R.id.endtripDate)
        discounts=itemView.findViewById<TextView>(R.id.tripDiscount)
        booktrip=itemView.findViewById<Button>(R.id.bookTrip)
        wishlist=itemView.findViewById<Button>(R.id.wishlist)

    }

}