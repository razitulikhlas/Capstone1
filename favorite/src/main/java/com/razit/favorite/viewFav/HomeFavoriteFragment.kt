package com.razit.favorite.viewFav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.razit.capstone1.R
import com.razit.core.adapter.SectionsPagerAdapter
import com.razit.favorite.databinding.FragmentFavoriteHomeBinding


class HomeFavoriteFragment : Fragment() {

    private val listFragment = arrayListOf(
        FilmFavoriteFragment.newInstance(com.razit.core.BuildConfig.MOVIES),
        FilmFavoriteFragment.newInstance(com.razit.core.BuildConfig.TVSHOW)
    )
    private var _binding: FragmentFavoriteHomeBinding? = null
    private val binding get() = _binding!!
    private var mediator: TabLayoutMediator? = null

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

        mediator = TabLayoutMediator(binding.tabs, binding.viewPagerLocal) { tab, position ->
            if (position == 0) {
                tab.text = getString(R.string.movies)
            } else if (position == 1) {
                tab.text = getString(R.string.tvShow)
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
        FilmFavoriteFragment.newInstance(null)
        mediator?.detach()
        mediator = null
        binding.viewPagerLocal.adapter = null
        _binding = null
        super.onDestroyView()
    }

}