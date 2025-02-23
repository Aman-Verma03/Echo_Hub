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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TweetActivity : AppCompatActivity() {

    private lateinit var edtEnterTweet:EditText
    private lateinit var btnUploadTweet:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tweet)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()

        btnUploadTweet.setOnClickListener{
            val tweet=edtEnterTweet.text.toString()
            addTweet(tweet)
            val intent= Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun addTweet(tweet:String){
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().uid.toString())
            .addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listOfTweets=snapshot.child("listOfTweets").value as MutableList<String>
                    listOfTweets.add(tweet)

                    uploadTweetList(listOfTweets)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun uploadTweetList(listOfTweets:List<String>) {

        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().uid.toString())
            .child("listOfTweets").setValue(listOfTweets)
        Toast.makeText(this,"Tweet Uploaded Successfully",Toast.LENGTH_SHORT).show()

    }

    private fun init(){
        edtEnterTweet=findViewById(R.id.edt_enter_tweet)
        btnUploadTweet=findViewById(R.id.btn_upload_tweet)
    }
}