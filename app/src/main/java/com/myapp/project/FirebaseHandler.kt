package com.myapp.project

import android.content.Context
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*

class FirebaseHandler<T> {

    var database=FirebaseDatabase.getInstance()
    lateinit var reference: DatabaseReference
    lateinit var tablename:String


    constructor(tablename:String)
    {
        this.tablename=tablename
    }
    fun insert(data:T):Boolean
    {
        var check:Boolean=false
        reference=database.getReference(tablename).push()

        reference.setValue(data).addOnCompleteListener { p0 -> check = p0.isSuccessful() }
        return check
    }
    fun getTripsData(data:ArrayList<Trips>)
    {

        database.getReference(tablename).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(d in snapshot.children)
                {
                    val answer=d.getValue(Trips::class.java)

                    data.add(answer!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }
}