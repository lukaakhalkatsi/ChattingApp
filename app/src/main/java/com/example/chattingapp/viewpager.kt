package com.example.chattingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class viewpager : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_viewpager, container, false)
        val pager = view.findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val adapter = fragmentManager?.let { ViewPagerAdapter(it, lifecycle) }

        pager.adapter = adapter

        TabLayoutMediator(tabLayout, pager){tab, position->
            when(position){
                0->{
                    tab.text = "Service"
                    tab.setIcon(R.drawable.ic_baseline_design_services_24)
                }
                1->{
                    tab.text = "Payment"
                    tab.setIcon(R.drawable.ic_baseline_payment_24)
                }
                2->{
                    tab.text = "Gifts"
                    tab.setIcon(R.drawable.ic_baseline_card_giftcard_24)
                }
            }
        }.attach()


        return view
    }

}