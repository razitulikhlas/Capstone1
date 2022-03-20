package com.razit.core.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(
    lifeCycle: Lifecycle,
    fm: FragmentManager,
    private val listFragment: List<Fragment>
) : FragmentStateAdapter(fm, lifeCycle) {

    override fun getItemCount(): Int = listFragment.size

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }
}