package com.razit.capstone1.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.razit.capstone1.R
import com.razit.capstone1.databinding.FragmentHomeBinding
import com.razit.core.adapter.SectionsPagerAdapter


class HomeFragment : Fragment() {

    private val listFragment = arrayListOf(
        FilmFragment.newInstance(com.razit.core.BuildConfig.MOVIES),
        FilmFragment.newInstance(com.razit.core.BuildConfig.TVSHOW)
    )
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var mediator: TabLayoutMediator? = null
    private var sectionsPagerAdapter: SectionsPagerAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sectionsPagerAdapter =
            SectionsPagerAdapter(lifecycle, childFragmentManager, listFragment)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.ivFavorite.setOnClickListener {
            startActivity(
                Intent(
                    requireActivity(),
                    Class.forName("com.razit.favorites.viewFav.FavoriteActivity")
                )
            )
        }

        binding.tvSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchMoviesFragment)
        }

        mediator = TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            if (position == 0) {
                tab.text = getString(R.string.movies)
            } else if (position == 1) {
                tab.text = getString(R.string.tvShow)
            }
        }
        mediator?.attach()


        binding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabs.selectTab(binding.tabs.getTabAt(position))
            }
        })
    }

    override fun onDestroyView() {
        mediator?.detach()
        mediator = null
        binding.viewPager.adapter = null
        sectionsPagerAdapter = null
        FilmFragment.newInstance(null)
        binding.viewPager.removeOnAttachStateChangeListener(null)
        binding.tabs.clearOnTabSelectedListeners()
        binding.tabs.setupWithViewPager(null)
        _binding = null
        super.onDestroyView()
    }

}