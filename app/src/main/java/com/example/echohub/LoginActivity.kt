package com.example.echohub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.echohub.data.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {

    private lateinit var edtPassword:EditText
    private lateinit var edtEmail:EditText
    private lateinit var btnSignUp:Button
    private lateinit var btnSignIn:Button
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()

        if(mAuth.currentUser != null){
            val intent=Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnSignIn.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            signIn(email,password)
        }

        btnSignUp.setOnClickListener{
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()
            signUp(email,password)
        }

    }

    private fun init(){
        edtPassword=findViewById(R.id.edt_password)
        edtEmail=findViewById(R.id.edt_email)
        btnSignUp=findViewById(R.id.btn_sign_up)
        btnSignIn=findViewById(R.id.btn_sign_in)
        mAuth=Firebase.auth
    }

    private fun signIn(email:String,password: String){
        mAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent=Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun signUp(email:String,password:String){
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val listOfFollowings=mutableListOf<String>()
                    listOfFollowings.add("")
                    val listOfTweets= mutableListOf<String>()
                    listOfTweets.add("")
//                    Add user data to firebase
                    val user=User(
                        userEmail =email,
                        userProfileImage = "",
                        listOfFollowings = listOfFollowings,
                        listOfTweets=listOfTweets,
                        uid=mAuth.uid.toString()
                    )
                    addUserToDatabase(user)
                    val intent=Intent(this,HomeActivity :: class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(user:User){
        Firebase.database.getReference("users").child(user.uid).setValue(user)
    }
}