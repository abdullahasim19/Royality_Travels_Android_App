package com.myapp.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*

class ShowTrips : AppCompatActivity() {

    lateinit var recycler: RecyclerView
    var check:Int=0
    lateinit var spinnerFilter: Spinner
    var database= FirebaseDatabase.getInstance()
    lateinit var reference: DatabaseReference
    lateinit var adapterCustom:CustomAdapterTripView
    lateinit var options: FirebaseRecyclerOptions<Trips>
    lateinit var adapter: FirebaseRecyclerAdapter<Trips, TripViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_trips)
        initialize()
        //SetRecyclerView()

    }
    private fun initialize()
    {
        recycler=findViewById<RecyclerView>(R.id.recycler)//recycler view
        spinnerFilter=findViewById<Spinner>(R.id.spinnerFilter)//spinner
        reference=database.reference.child("Trips")
        var arr= ArrayAdapter.createFromResource(
            this,R.array.filterings,android.R.layout.simple_spinner_item)//setting array adapter for spinner
        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFilter.adapter=arr

        var tripsarray:List<Trips>
        tripsarray=ArrayList<Trips>()
        adapterCustom= CustomAdapterTripView(tripsarray,this)
        recycler.layoutManager=LinearLayoutManager(this)
        recycler.adapter=adapterCustom

        database.getReference("Trips").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(d in snapshot.children)
                {
                    val answer=d.getValue(Trips::class.java)
                    tripsarray.add(answer!!)
                }
                adapterCustom.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
//        spinnerFilter.setOnItemSelectedListener(object :AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                if(adapter!=null)
//                {
//                    adapter!!.setSearchType(parent!!.getItemAtPosition(position).toString())
//                }
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//        })

//        try {
//            val searchResult=findViewById<SearchView>(R.id.searchBar)
//            searchResult.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    return false
//                }
//
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    adapter!!.getFilter().filter(newText)
//                    return true
//                }
//
//            })
//        }
//        catch (ex:Exception)
//        {
//            Toast.makeText(this,ex.message.toString(),Toast.LENGTH_LONG).show()
//        }
    }

//    private fun SetRecyclerView()
//    {
//        reference=database.getReference().child("Trips")
//        //Toast.makeText(applicationContext,reference.key!!.toString(),Toast.LENGTH_SHORT).show()
//        //return
//        options=FirebaseRecyclerOptions.Builder<Trips>().setQuery(reference,Trips::class.java).build()
//        adapter = object : FirebaseRecyclerAdapter<Trips, TripViewHolder>(options) {
//            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
//
//                val v:View= LayoutInflater.from(parent.getContext()).inflate(R.layout.trips_view,parent,false)
//                return TripViewHolder(v)
//            }
//
//            override fun onBindViewHolder(holder: TripViewHolder, position: Int, model: Trips) {
//                holder.id.text=model.id.toString()
//                holder.location.text=model.location
//                holder.seats.text=model.seats.toString()
//                holder.startdate.text=model.starttripDate
//                holder.enddate.text=model.endtripDate
//
//                val checkDiscount=model.discount
//                if (checkDiscount<=0)
//                {
//                    holder.discounts.text = "No Discount"
//                    holder.discounts.setTextColor(resources.getColor(R.color.design_default_color_error))
//                }
//                else {
//                    holder.discounts.text = checkDiscount.toString()
//                    holder.discounts.setTextColor(resources.getColor(R.color.green))
//                }
//                holder.booktrip.setOnClickListener {
//                    Toast.makeText(applicationContext,"Book",Toast.LENGTH_SHORT).show()
//
//                }
//                holder.wishlist.setOnClickListener {
//                    Toast.makeText(applicationContext,"Wish",Toast.LENGTH_SHORT).show()
//                }
//
//            }
//        }
//        recycler.layoutManager= LinearLayoutManager(this)
//        adapter.startListening()
//        recycler.adapter=adapter
//    }

}