package com.myapp.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*

class UserWishlist : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    var check:Int=0
    lateinit var spinnerFilter: Spinner
    var database= FirebaseDatabase.getInstance()
    lateinit var reference: DatabaseReference
    lateinit var adapterCustom:CustomAdapterWishlist
    lateinit var options: FirebaseRecyclerOptions<Trips>
    lateinit var adapter: FirebaseRecyclerAdapter<Trips, TripViewHolder>
    lateinit var userData:User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_wishlist)
        try {
            userData=intent.getSerializableExtra("UserInfo") as User
        }
        catch (e:Exception)
        {
            Toast.makeText(this,e.message.toString(), Toast.LENGTH_SHORT).show()
        }
        initialize()

    }
    private fun initialize()
    {
        recycler=findViewById<RecyclerView>(R.id.recyclerWishlist)//recycler view
        spinnerFilter=findViewById<Spinner>(R.id.spinnerFilterWish)//spinner
        reference=database.reference.child("Wishlist")
        var arr= ArrayAdapter.createFromResource(
            this,R.array.filterings,android.R.layout.simple_spinner_item)//setting array adapter for spinner
        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFilter.adapter=arr

        var wisharray:List<WishListData>
        wisharray=ArrayList<WishListData>()
        adapterCustom= CustomAdapterWishlist(wisharray,this,userData)
        recycler.layoutManager= LinearLayoutManager(this)
        recycler.adapter=adapterCustom

        database.getReference("Wishlist").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(d in snapshot.children)
                {
                    val answer=d.getValue(WishListData::class.java)
                    if(answer!!.emailWish==userData.email)
                    {
                        wisharray.add(answer)
                    }
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
}