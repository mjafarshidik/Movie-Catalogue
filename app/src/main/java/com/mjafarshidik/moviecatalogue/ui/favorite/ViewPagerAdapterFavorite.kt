package com.mjafarshidik.moviecatalogue.ui.favorite

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapterFavorite (private val fragmentList: List<Fragment>,
fm: FragmentManager,
lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}