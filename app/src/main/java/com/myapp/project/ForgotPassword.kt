package com.myapp.project

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

class ForgotPassword : AppCompatActivity() {
    lateinit var emailRef:AppCompatEditText
    lateinit var resetButton:Button
    var auth=FirebaseAuth.getInstance()
    var check:Boolean=false
    var loadingdialog=LoadingDialog(this@ForgotPassword)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        emailRef=findViewById<AppCompatEditText>(R.id.emailTextForgot)
        resetButton=findViewById<Button>(R.id.btnReset)

        resetButton.setOnClickListener {
            check=false
            val emailEntered=emailRef.text.toString()
            loadingdialog.startLoading()
            checkValid(emailEntered)
        }

    }
    fun checkValid(checkmail:String)
    {
        val reference= FirebaseDatabase.getInstance()
        reference.getReference("Users").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(d in snapshot.children)
                {
                    val answer=d.getValue(User::class.java)
                    if(answer!!.email==checkmail)
                    {
                        check=true
                        auth.sendPasswordResetEmail(checkmail).addOnCompleteListener(){task->
                            if(task.isSuccessful)
                            {
                                Toast.makeText(applicationContext,"Password Reset Mail Sent",
                                    Toast.LENGTH_SHORT).show()

                            }

                        }
                        break
                    }
                }
                loadingdialog.dismissDialog()
                if(check==false)
                {
                    Toast.makeText(applicationContext,"Invalid Email",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }

}