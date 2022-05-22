package com.myapp.project

import android.content.Context
import android.util.Log
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

        try {
            reference.setValue(data).addOnCompleteListener {
                    p0 -> check = p0.isSuccessful ;

            }
        }
        catch (e:Exception) {
            Log.d("Firebase Error",e.message.toString())
        }

        return check
    }
}