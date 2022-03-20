package com.razit.favorite.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.razit.core.adapter.SectionsPagerAdapter
import com.razit.favorite.databinding.FragmentFavoriteHomeBinding


class HomeFavoriteFragment : Fragment() {

    private val listFragment = arrayListOf(
        FilmFragment.newInstance(com.razit.core.BuildConfig.MOVIES),
        FilmFragment.newInstance(com.razit.core.BuildConfig.TVSHOW)
    )
    private var _binding: FragmentFavoriteHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sectionsPagerAdapter =
            SectionsPagerAdapter(lifecycle, childFragmentManager, listFragment)
        binding.viewPagerLocal.adapter = sectionsPagerAdapter
        binding.tabs.addTab(binding.tabs.newTab().setText(com.razit.capstone1.R.string.movies))
        binding.tabs.addTab(binding.tabs.newTab().setText(com.razit.capstone1.R.string.tvShow))


        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let {
                    binding.viewPagerLocal.currentItem = it
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        binding.viewPagerLocal.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabs.selectTab(binding.tabs.getTabAt(position))
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}