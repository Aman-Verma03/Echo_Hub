package com.example.echohub.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.echohub.SuggestedAccountFragment
import com.example.echohub.TweetFragment

class VPAdapter(fa:FragmentActivity):FragmentStateAdapter (fa){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0->{
                SuggestedAccountFragment()
            }else->{
                TweetFragment()
            }
        }

    }
}