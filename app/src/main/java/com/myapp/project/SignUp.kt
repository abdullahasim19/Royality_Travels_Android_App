package com.myapp.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    lateinit var email: AppCompatEditText
    lateinit var pass: AppCompatEditText
    lateinit var nameEdit:AppCompatEditText
    lateinit var phone:AppCompatEditText
    lateinit var signUp: Button
    var auth=FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        register()
        signUp.setOnClickListener {
            var mail=email.text.toString()
            var password=pass.text.toString()
            val username=nameEdit.text.toString()
            val numberphone=phone.text.toString()

            signUpFirebase(mail,password,username,numberphone)
        }
    }

    private fun signUpFirebase(userEmail:String,userPassword:String,username:String,numberphone:String)
    {
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val dataInsert=User(userEmail,userPassword,username,numberphone)
                    val fb=FirebaseHandler<User>("Users")
                    fb.insert(dataInsert)
                    Toast.makeText(this,"Account Created",Toast.LENGTH_SHORT).show()
                    finish()
                }
                else
                {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun register()
    {
        email=findViewById<AppCompatEditText>(R.id.emailText)
        pass=findViewById<AppCompatEditText>(R.id.passwordText)
        signUp=findViewById<Button>(R.id.btnSignUp)
        nameEdit=findViewById<AppCompatEditText>(R.id.userNameSignUp)
        phone=findViewById<AppCompatEditText>(R.id.userPhoneSignUp)
    }

}