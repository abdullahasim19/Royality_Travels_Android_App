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

class ShowHistory : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    var check:Int=0
    lateinit var spinnerFilter: Spinner
    var database= FirebaseDatabase.getInstance()
    lateinit var reference: DatabaseReference
    lateinit var adapterCustom:CustomAdapterHistory
    lateinit var options: FirebaseRecyclerOptions<Trips>
   // lateinit var adapter: FirebaseRecyclerAdapter<Trips, TripViewHolder>
    lateinit var userData:User
    var loadingDialog: LoadingDialog= LoadingDialog(this@ShowHistory)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //userData=User()
        //userData.email="abdullah@gmail"
        try {
            userData=intent.getSerializableExtra("UserInfo") as User
        }
        catch (e:Exception)
        {
            Toast.makeText(this,e.message.toString(), Toast.LENGTH_SHORT).show()
        }
        setContentView(R.layout.activity_show_history)
        loadingDialog.startLoading()
        initialize()
    }
    private fun initialize()
    {
        recycler=findViewById<RecyclerView>(R.id.recyclerHistory)//recycler view
        spinnerFilter=findViewById<Spinner>(R.id.spinnerFilterHist)//spinner
        reference=database.reference.child("History")
        var arr= ArrayAdapter.createFromResource(
            this,R.array.history,android.R.layout.simple_spinner_item)//setting array adapter for spinner
        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFilter.adapter=arr

        var Histarray:List<UserHistory>
        Histarray=ArrayList<UserHistory>()
        var HistarrayFull:List<UserHistory>
        HistarrayFull=ArrayList<UserHistory>()

        adapterCustom= CustomAdapterHistory(Histarray,HistarrayFull,this,userData)
        recycler.layoutManager= LinearLayoutManager(this)
        recycler.adapter=adapterCustom

        database.getReference("History").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(d in snapshot.children)
                {
                    val answer=d.getValue(UserHistory::class.java)
                    if(answer!!.email==userData.email)
                    {
                        Histarray.add(answer)
                        HistarrayFull.add(answer)
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
            val searchResult=findViewById<SearchView>(R.id.searchBarHist)
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