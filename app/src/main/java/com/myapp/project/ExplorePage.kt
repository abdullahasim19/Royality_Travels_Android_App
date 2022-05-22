package com.myapp.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*

class ExplorePage : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    var check:Int=0
    //lateinit var spinnerFilter: Spinner
    var database= FirebaseDatabase.getInstance()
    lateinit var reference: DatabaseReference
    lateinit var adapterCustom:CustomAdapterExplore
    lateinit var options: FirebaseRecyclerOptions<Trips>
    lateinit var adapter: FirebaseRecyclerAdapter<Trips, TripViewHolder>
    lateinit var explorearray:List<TripReview>
    var loadingDialog=LoadingDialog(this@ExplorePage)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore_page)
        val b=findViewById<Button>(R.id.sortingBest)
        val c=findViewById<Button>(R.id.sortingWorst)
        loadingDialog.startLoading()
        initialize()
        b.setOnClickListener {
            explorearray=explorearray.sortedByDescending { it.ratingofTrip }

           adapterCustom.reviews=explorearray
            recycler.adapter=adapterCustom

        }

        c.setOnClickListener {
            explorearray=explorearray.sortedBy { it.ratingofTrip }

            adapterCustom.reviews=explorearray
            recycler.adapter=adapterCustom

        }
    }
    private fun initialize()
    {
        recycler=findViewById<RecyclerView>(R.id.recyclerExplore)//recycler view

        reference=database.reference.child("Reviews")

        explorearray=ArrayList<TripReview>()
        adapterCustom= CustomAdapterExplore(explorearray,this)
        recycler.layoutManager= LinearLayoutManager(this)
        recycler.adapter=adapterCustom


        database.getReference("Reviews").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(d in snapshot.children)
                {
                    val answer=d.getValue(TripReview::class.java)


                    if (answer != null) {
                        (explorearray as ArrayList<TripReview>).add(answer)
                    }

                }
                adapterCustom.notifyDataSetChanged()
                loadingDialog.dismissDialog()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.message.toString(),Toast.LENGTH_SHORT).show()
            }

        })
    }

}