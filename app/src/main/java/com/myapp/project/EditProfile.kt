package com.myapp.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EditProfile : AppCompatActivity() {
    lateinit var userData:User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        try {
            userData=intent.getSerializableExtra("UserInfo") as User
        }
        catch (e:Exception)
        {
            Toast.makeText(this,e.message.toString(), Toast.LENGTH_SHORT).show()
        }
        val a=findViewById<Button>(R.id.btneditname)
        val b=findViewById<Button>(R.id.btneditphone)
        val c=findViewById<Button>(R.id.btneditemail)
        val d=findViewById<Button>(R.id.btneditpassword)
        val nameRef=findViewById<AppCompatEditText>(R.id.editnamedetails)
        val phoneref=findViewById<AppCompatEditText>(R.id.editphonedetails)
        val emailRef=findViewById<AppCompatEditText>(R.id.editemaildetails)
        val passRef=findViewById<AppCompatEditText>(R.id.editpassworddetails)

        a.setOnClickListener {
            val n=nameRef.text.toString()
            userData.userNameTrip=n
            UpdateInfo()
            Toast.makeText(this,"Name Updated",Toast.LENGTH_SHORT).show()
            val intent= Intent()
            intent.putExtra("UserInfoUpdated",userData)
            setResult(RESULT_OK,intent)
        }

        b.setOnClickListener {
            val n=phoneref.text.toString()
            userData.phoneNumber=n
            UpdateInfo()
            Toast.makeText(this,"Name Updated",Toast.LENGTH_SHORT).show()
            val intent= Intent()
            intent.putExtra("UserInfoUpdated",userData)
            setResult(RESULT_OK,intent)
        }

        c.setOnClickListener {
            val n=emailRef.text.toString()
            val user=FirebaseAuth.getInstance().currentUser
            val old=user!!.email.toString()
            user!!.updateEmail(n).addOnCompleteListener(this) {task->
                if(task.isSuccessful){
                    userData.email=n
                    UpdateInfoEmail(old)
                    Toast.makeText(applicationContext,"Email Updated",Toast.LENGTH_SHORT).show()
                    val intent= Intent()
                    intent.putExtra("UserInfoUpdated",userData)
                    setResult(RESULT_OK,intent)
                }
            }
        }

        d.setOnClickListener {
            val n=passRef.text.toString()
            val user=FirebaseAuth.getInstance().currentUser
            user!!.updatePassword(n).addOnCompleteListener(this) {task->
                if(task.isSuccessful)
                {
                    userData.password=n
                    UpdateInfo()
                    Toast.makeText(applicationContext,"Email Updated",Toast.LENGTH_SHORT).show()
                    val intent= Intent()
                    intent.putExtra("UserInfoUpdated",userData)
                    setResult(RESULT_OK,intent)
                }
            }
        }

    }
    fun UpdateInfoEmail(oldemail:String)
    {
        val reference= FirebaseDatabase.getInstance()
        reference.getReference("Users").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(d in snapshot.children)
                {

                    val answer=d.getValue(User::class.java)
                    if(answer!!.email==oldemail)
                    {
                        d.ref.setValue(userData)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
    fun UpdateInfo()
    {
        val reference= FirebaseDatabase.getInstance()
        reference.getReference("Users").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(d in snapshot.children)
                {
                //    Toast.makeText(applicationContext,"in",Toast.LENGTH_SHORT).show()
                    val answer=d.getValue(User::class.java)
                    if(answer!!.email==userData.email)
                    {
                        d.ref.setValue(userData)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}