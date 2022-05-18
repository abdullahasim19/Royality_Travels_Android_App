package com.myapp.project

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.RenderScript
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.*

class Firebase : AppCompatActivity() {

    var database=FirebaseDatabase.getInstance()
    lateinit var reference:DatabaseReference
    lateinit var rview:RecyclerView
    lateinit var options: FirebaseRecyclerOptions<Trips>
    lateinit var adapter:FirebaseRecyclerAdapter<Trips,TripViewHolder>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)

        val data=FirebaseHandler<TourPlans>("Tour Plans")

        val d1=TourPlans(1,"Swat","Departure at 1 stay of 2 hours")
        val d2=TourPlans(2,"Hunza","Departure at 1 stay of 2 hours")
        val d3 = TourPlans(3,"Feary Meadows","Departure at 1 stay of 2 hours")

        data.insert(d1)
        data.insert(d2)
        data.insert(d3)


//
//        val b=findViewById<Button>(R.id.button)
//
//        b.setOnClickListener {
//            ShowNotification("hello","world")
//        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun ShowNotification(title:String,text:String)
    {
        var channel=NotificationChannel("1","1",NotificationManager.IMPORTANCE_DEFAULT)
        var manager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

        var builder = Notification.Builder(this,"1",)
        builder.setSmallIcon(R.drawable.ic_stat_add_alert).setContentTitle(title).setContentText(text)

        var compact = NotificationManagerCompat.from(this,)
        compact.notify(1,builder.build())

    }

//        database.getReference("Trips").addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for(d in snapshot.children)
//                {
//                    val answer=d.getValue(Trips::class.java)
//                    answer!!.price=1500
//                    if(answer.id==1)
//                        d.ref.setValue(answer)
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//
//        })


       // rview=findViewById<RecyclerView>(R.id.r1)
        //reference=database.getReference().child("Trips")

//        var insertion=FirebaseHandler<Trips>("Trips")
//        var data=Trips(1,"Swat",10,"20 January","10 February",1.0)
//
//        var data1=Trips(2,"Hunza",10,"20 January","10 February",0.0)
//        var data2=Trips(3,"Feary Meadows",10,"20 January","10 February",1.0)
//        insertion.insert(data)
//
//        insertion.insert(data1)
//        insertion.insert(data2)















    //}
//    fun SetRecyclerView()
//    {
//        options=FirebaseRecyclerOptions.Builder<Trips>().setQuery(reference,Trips::class.java).build()
//
//        adapter = object : FirebaseRecyclerAdapter<Trips, TripViewHolder>(options) {
//            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
//
//                val v:View=LayoutInflater.from(parent.getContext()).inflate(R.layout.demolayout,parent,false)
//                return TripViewHolder(v)
//            }
//
//            override fun onBindViewHolder(holder: TripViewHolder, position: Int, model: Trips) {
//                holder.name.text=model.name
//                holder.seats.text=model.seats.toString()
//                holder.tbutton.setOnClickListener {
//                    Toast.makeText(applicationContext,holder.name.text,Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//        rview.layoutManager=LinearLayoutManager(this)
//        adapter.startListening()
//        rview.adapter=adapter
//    }
}