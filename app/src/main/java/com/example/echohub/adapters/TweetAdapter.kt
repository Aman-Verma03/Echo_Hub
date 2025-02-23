package com.example.echohub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.echohub.R
import com.example.echohub.data.Tweet

class TweetAdapter(
    private val listOfTweet:List<Tweet>,
)
    : RecyclerView.Adapter<TweetAdapter.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textTweet:TextView=itemView.findViewById(R.id.text_tweet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.layout_tweet,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTweet=listOfTweet[position]
        holder.textTweet.text=currentTweet.tweetContent
    }

    override fun getItemCount(): Int {
        return listOfTweet.size
    }

}