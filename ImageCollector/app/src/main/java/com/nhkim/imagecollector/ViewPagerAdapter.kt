package com.nhkim.imagecollector

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nhkim.imagecollector.ui.favorites.FavoritesFragment
import com.nhkim.imagecollector.ui.search.ImageSearchFragment

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    private val TAB_COUNT = 2
    override fun getItemCount(): Int {
        return TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ImageSearchFragment()
            1 -> FavoritesFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}