package com.myapp.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*

class ViewTourPlans : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    var check:Int=0
    lateinit var spinnerFilter: Spinner
    var database= FirebaseDatabase.getInstance()
    lateinit var reference: DatabaseReference
    lateinit var adapterCustom:CustomAdapterTourPlan
    lateinit var options: FirebaseRecyclerOptions<Trips>
    //lateinit var adapter: FirebaseRecyclerAdapter<Trips, TripViewHolder>
    var loadingDialog=LoadingDialog(this@ViewTourPlans)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_tour_plans)
        loadingDialog.startLoading()
        initialize()
    }
    private fun initialize()
    {
        recycler=findViewById<RecyclerView>(R.id.recyclerTourPlan)//recycler view
        spinnerFilter=findViewById<Spinner>(R.id.spinnerFilterTour)//spinner
        reference=database.reference.child("Tour Plans")
        var arr= ArrayAdapter.createFromResource(
            this,R.array.tourplan,android.R.layout.simple_spinner_item)//setting array adapter for spinner
        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFilter.adapter=arr

        var planarray:List<TourPlans>
        planarray=ArrayList<TourPlans>()

        var planarrayFull:List<TourPlans>
        planarrayFull=ArrayList<TourPlans>()

        adapterCustom= CustomAdapterTourPlan(planarray,planarrayFull,this)
        recycler.layoutManager= LinearLayoutManager(this)
        recycler.adapter=adapterCustom

        database.getReference("Tour Plans").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(d in snapshot.children)
                {
                    val answer=d.getValue(TourPlans::class.java)

                    if (answer != null) {
                        planarray.add(answer)
                        planarrayFull.add(answer)
                    }

                }
                adapterCustom.notifyDataSetChanged()
                loadingDialog.dismissDialog()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        spinnerFilter.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(adapterCustom!=null)
                {
                    adapterCustom!!.setSearchType(parent!!.getItemAtPosition(position).toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        })

        try {
            val searchResult=findViewById<SearchView>(R.id.searchBarTour)
            searchResult.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapterCustom!!.getFilter().filter(newText)
                    return true
                }

            })
        }
        catch (ex:Exception)
        {
            Toast.makeText(this,ex.message.toString(),Toast.LENGTH_LONG).show()
        }
    }
}