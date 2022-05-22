package com.myapp.project

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BookingActivity : AppCompatActivity() {
    lateinit var userData:User
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        val data=intent.getSerializableExtra("trips") as Trips
        try {
            userData=intent.getSerializableExtra("UserInfo") as User
        }
        catch (e:Exception)
        {
            Toast.makeText(this,e.message.toString(),Toast.LENGTH_SHORT).show()
        }
        val showTheName=findViewById<TextView>(R.id.userNameBooking)
        showTheName.text=userData.userNameTrip.toString()

        val location=findViewById<TextView>(R.id.lname)
        val price=findViewById<TextView>(R.id.lprice)
        val seats=findViewById<TextView>(R.id.lseats)
        val start=findViewById<TextView>(R.id.lstart)
        val end=findViewById<TextView>(R.id.lend)
        val dis=findViewById<TextView>(R.id.ldiscount)

        val btn=findViewById<Button>(R.id.tripBook)
        val seatsentered=findViewById<AppCompatEditText>(R.id.Seats)

        location.text=location.text.toString().plus( data.location)
        price.text=price.text.toString().plus(data.price.toString())
        seats.text=seats.text.toString().plus(data.seats.toString())
        start.text=start.text.toString().plus(data.starttripDate)
        end.text=end.text.toString().plus(data.endtripDate)
        dis.text=dis.text.toString().plus(data.discount)

        btn.setOnClickListener {

            val seatsEntered=seatsentered.text.toString().toInt()
            var packagee:String="None"
            if (seatsEntered<0 || seatsEntered>data.seats) {
                Toast.makeText(applicationContext,"Not enough Seats",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val insertion=FirebaseHandler<UserHistory>("History")
            val tempData=data
            tempData.seats=seatsEntered
            if(seatsEntered>=5 && seatsEntered<10)
            {
                packagee="Bronze"
                tempData.discount+=5.0
                tempData.discount=tempData.discount/100
                tempData.price= (tempData.price-(tempData.price*tempData.discount)).toInt()
            }
            else if(seatsEntered>=10 && seatsEntered<=15)
            {
                packagee="Silver"
                tempData.discount+=10.0
                tempData.discount=tempData.discount/100
                tempData.price= (tempData.price-(tempData.price*tempData.discount)).toInt()
            }
            else if(seatsEntered>=15 && seatsEntered<=20)
            {
                packagee="Gold"
                tempData.discount+=15.0
                tempData.discount=tempData.discount/100
                tempData.price= (tempData.price-(tempData.price*tempData.discount)).toInt()
            }
            val dataToInsert=UserHistory(userData.email,tempData,packagee)
            insertion.insert(dataToInsert)
            UpdateSeats("Trips",data,seatsEntered)
            ShowNotification("Royality Travels","Booking Done")
            Toast.makeText(this,"Booking Done",Toast.LENGTH_SHORT).show()
        }
    }
    fun UpdateSeats(tableName:String,details:Trips,bookSeats:Int)
    {
        val reference=FirebaseDatabase.getInstance()
        reference.getReference(tableName).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(d in snapshot.children)
                {
                    val answer=d.getValue(Trips::class.java)
                    if(answer!!.id==details.id)
                    {
                        //Toast.makeText(applicationContext,"In",Toast.LENGTH_SHORT).show()
                        val temp=answer.seats-bookSeats
                        answer.seats=temp
                        d.ref.setValue(answer)
                        break
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun ShowNotification(title:String,text:String)
    {
        var channel= NotificationChannel("1","1", NotificationManager.IMPORTANCE_DEFAULT)
        var manager=getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

        var builder = Notification.Builder(this,"1",)
        builder.setSmallIcon(R.drawable.ic_stat_add_alert).setContentTitle(title).setContentText(text)

        var compact = NotificationManagerCompat.from(this)
        compact.notify(1,builder.build())

    }
}