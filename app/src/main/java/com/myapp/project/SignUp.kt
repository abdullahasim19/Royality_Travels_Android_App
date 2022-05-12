package com.myapp.project

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {
    lateinit var email: AppCompatEditText
    lateinit var pass: AppCompatEditText
    lateinit var signUp: Button
    var auth=FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        register()
        signUp.setOnClickListener {
            var mail=email.text.toString()
            var password=pass.text.toString()
            signUpFirebase(mail,password)
        }
    }

    private fun signUpFirebase(userEmail:String,userPassword:String)
    {
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this,"Login Done",Toast.LENGTH_SHORT).show()

                }
                else
                {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"Login Fail",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun register()
    {
        email=findViewById<AppCompatEditText>(R.id.emailText)
        pass=findViewById<AppCompatEditText>(R.id.passwordText)
        signUp=findViewById<Button>(R.id.btnSignUp)
    }


}