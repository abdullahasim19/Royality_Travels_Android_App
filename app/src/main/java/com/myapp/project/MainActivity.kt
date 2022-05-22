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

class MainActivity : AppCompatActivity() {
    lateinit var email:AppCompatEditText
    lateinit var pass:AppCompatEditText
    lateinit var login:Button
    lateinit var signUp: Button
    lateinit var forgotPass:Button
    var auth=FirebaseAuth.getInstance()
    var loadingdialog:LoadingDialog=LoadingDialog(this@MainActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        register()

        login.setOnClickListener {
            val userEmail=email.text.toString()
            var userPass=pass.text.toString()

            loadingdialog.startLoading()

            signInFirebase(userEmail,userPass)

        }
        signUp.setOnClickListener {
            val i=Intent(this,SignUp::class.java)
            startActivity(i)
        }

        forgotPass.setOnClickListener {
            val i=Intent(this,ForgotPassword::class.java)
            startActivity(i)
        }
    }
    private fun signInFirebase(mailUser:String,passUser:String)
    {
        auth.signInWithEmailAndPassword(mailUser, passUser)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this,"Logging In",Toast.LENGTH_SHORT).show()
                    val i =Intent(this,MainPage::class.java)

                    val reference= FirebaseDatabase.getInstance()
                    reference.getReference("Users").addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for(d in snapshot.children)
                            {
                                val answer=d.getValue(User::class.java)
                                if(answer!!.email==mailUser)
                                {
                                    loadingdialog.dismissDialog()
                                    i.putExtra("UserInfo",answer)
                                    startActivity(i)
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                        }

                    })

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                    loadingdialog.dismissDialog()
                }
            }
    }
    private fun searchUser(emailOftheUser:String):User
    {
        var temp=User()
        val reference= FirebaseDatabase.getInstance()
        reference.getReference("Users").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(d in snapshot.children)
                {
                    val answer=d.getValue(User::class.java)
                    if(answer!!.email==emailOftheUser)
                    {
                        temp=answer
                        break
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return temp
    }
    private fun register()
    {
        email=findViewById<AppCompatEditText>(R.id.emailText)
        pass=findViewById<AppCompatEditText>(R.id.passwordText)
        login=findViewById<Button>(R.id.btnLogin)
        signUp=findViewById<Button>(R.id.btnSignUp)
        forgotPass=findViewById<Button>(R.id.btnForgot)
    }

    override fun onStart() {
        super.onStart()

        var user=auth.currentUser
        if(user!=null)
        {

            loadingdialog.startLoading()
            val i =Intent(this,MainPage::class.java)
            val reference= FirebaseDatabase.getInstance()
            reference.getReference("Users").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(d in snapshot.children)
                    {
                        val answer=d.getValue(User::class.java)
                        if(answer!!.email==user.email)
                        {
                            loadingdialog.dismissDialog()
                            i.putExtra("UserInfo",answer)
                            startActivity(i)
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }

            })

        }
    }

}