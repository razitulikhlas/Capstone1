package com.razit.favorites.viewFav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.razit.core.adapter.SectionsPagerAdapter
import com.razit.favorites.databinding.FragmentHomeFavoriteBinding


class HomeFavoriteFragment : Fragment() {
    private val listFragment = arrayListOf(
        FilmFragment.newInstance(com.razit.core.BuildConfig.MOVIES),
        FilmFragment.newInstance(com.razit.core.BuildConfig.TVSHOW)
    )
    private var _binding: FragmentHomeFavoriteBinding? = null
    private val binding get() = _binding!!
    private var mediator: TabLayoutMediator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sectionsPagerAdapter =
            SectionsPagerAdapter(lifecycle, childFragmentManager, listFragment)
        binding.viewPagerLocal.adapter = sectionsPagerAdapter

        mediator = TabLayoutMediator(binding.tabs, binding.viewPagerLocal) { tab, position ->
            if (position == 0) {
                tab.text = getString(com.razit.capstone1.R.string.movies)
            } else if (position == 1) {
                tab.text = getString(com.razit.capstone1.R.string.tvShow)
            }
        }
        mediator?.attach()

        binding.viewPagerLocal.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabs.selectTab(binding.tabs.getTabAt(position))
            }
        })
    }


    override fun onDestroyView() {
        FilmFragment.newInstance(null)
        mediator?.detach()
        mediator = null
        binding.viewPagerLocal.adapter = null
        _binding = null
        super.onDestroyView()
    }


}