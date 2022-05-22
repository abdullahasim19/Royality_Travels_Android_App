package com.myapp.project

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView

class CustomAdapterHistory(var hist:List<UserHistory>,var histFull:List<UserHistory>,var context:Context,var userInfo:User):
    RecyclerView.Adapter<CustomAdapterHistory.ViewHolderHistory>(),Filterable {

    var filterType="ID"

    class ViewHolderHistory(ItemView: View):RecyclerView.ViewHolder(ItemView){
        val id=itemView.findViewById<TextView>(R.id.HistorytripID)
        val location=itemView.findViewById<TextView>(R.id.HistorytripLocation)
        val seats=itemView.findViewById<TextView>(R.id.HistorytripSeats)
        val startdate=itemView.findViewById<TextView>(R.id.HistorystarttripDate)
        val enddate=itemView.findViewById<TextView>(R.id.HistoryendtripDate)
        val discounts=itemView.findViewById<TextView>(R.id.HistorytripDiscount)
        val review=itemView.findViewById<Button>(R.id.giveReview)
        val price=itemView.findViewById<TextView>(R.id.HistorytripPrice)
        val pack=itemView.findViewById<TextView>(R.id.HistoryPackage)
        val give=itemView.findViewById<AppCompatEditText>(R.id.reviewToGive)
        val ratings=itemView.findViewById<AppCompatEditText>(R.id.ratingToGive)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHistory {
        val v:View= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_view,parent,false)
        return CustomAdapterHistory.ViewHolderHistory(v)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolderHistory, position: Int) {
        holder.id.text=hist[position].tripdetails.id.toString()

        holder.location.text=hist[position].tripdetails.location
        holder.seats.text=hist[position].tripdetails.seats.toString()
        holder.startdate.text=hist[position].tripdetails.starttripDate
        holder.enddate.text=hist[position].tripdetails.endtripDate
        holder.price.text=hist[position].tripdetails.price.toString()
        val checkDiscount=hist[position].tripdetails.discount
        if (checkDiscount<=0)
        {
            holder.discounts.text = "No Discount"
            holder.discounts.setTextColor(context.resources.getColor(R.color.design_default_color_error))
        }
        else {
            holder.discounts.text = (checkDiscount*100.0).toString()
            holder.discounts.setTextColor(context.resources.getColor(R.color.green))
        }
        val p=hist[position].curPackage
        holder.pack.text=p

        holder.review.setOnClickListener {
            val remail=userInfo.email
            val givens=holder.give.text.toString()

            if(givens.isEmpty())
            {
                Toast.makeText(context,"Kindly Write your Review",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(holder.ratings.text.toString().isEmpty())
            {
                Toast.makeText(context,"Kindly Write your Rating",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val ratingGivens=holder.ratings.text.toString().toDouble()
            val dataToInsert=TripReview(remail,hist[position].tripdetails.id,hist[position].tripdetails.location,givens,ratingGivens)
            val doits=FirebaseHandler<TripReview>("Reviews")
            doits.insert(dataToInsert)
            Toast.makeText(context,"Review Given",Toast.LENGTH_SHORT).show()
            ShowNotification("Royality Travels","Review and Rating Done")
        }
    }

    override fun getItemCount(): Int {
        return hist.size
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun ShowNotification(title:String,text:String)
    {
        var channel= NotificationChannel("1","1", NotificationManager.IMPORTANCE_DEFAULT)
        var manager=context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

        var builder = Notification.Builder(context,"1",)
        builder.setSmallIcon(R.drawable.ic_stat_add_alert).setContentTitle(title).setContentText(text)

        var compact = NotificationManagerCompat.from(context)
        compact.notify(1,builder.build())

    }
    fun setSearchType(type:String)
    {
        this.filterType=type
    }
    override fun getFilter(): Filter {
        return histFilter
    }

    var histFilter=object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var tempList = ArrayList<UserHistory>()
            var filteredList: List<UserHistory>
            if (constraint == null || constraint.length == 0) {
                filteredList = histFull.toList()
            } else {
                var pattern = constraint.toString().toLowerCase().trim()
                for (item: UserHistory in histFull) {
                    if (filterType == "ID") {
                        if (item.tripdetails.id.toString().toLowerCase().contains(pattern)) {
                            tempList.add(item)
                        }
                    } else if (filterType == "Location") {
                        if (item.tripdetails.location.toLowerCase().contains(pattern)) {
                            tempList.add(item)
                        }
                    } else if (filterType == "Seats") {
                        if (item.tripdetails.seats.toString().toLowerCase().contains(pattern)) {
                            tempList.add(item)
                        }
                    } else if (filterType == "Price") {
                        if (item.tripdetails.price.toString().toLowerCase().contains(pattern)) {
                            tempList.add(item)
                        }
                    } else if (filterType == "Package") {
                        if (item.curPackage.toLowerCase().contains(pattern)) {
                            tempList.add(item)
                        }
                    }

                }
                filteredList = tempList.toList()
            }

            var results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            var temps = results!!.values as List<UserHistory>
            hist = temps.toList()
            notifyDataSetChanged()
        }
    }

}