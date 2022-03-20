package com.razit.capstone1.view
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.razit.capstone1.R
import com.razit.core.adapter.SectionsPagerAdapter
import com.razit.capstone1.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private val listFragment = arrayListOf(
        FilmFragment.newInstance(com.razit.core.BuildConfig.MOVIES),
        FilmFragment.newInstance(com.razit.core.BuildConfig.TVSHOW)
    )
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sectionsPagerAdapter =
            SectionsPagerAdapter(lifecycle, childFragmentManager, listFragment)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.addTab(binding.tabs.newTab().setText(R.string.movies))
        binding.tabs.addTab(binding.tabs.newTab().setText(R.string.tvShow))
        binding.ivSetting.setOnClickListener {
            startActivity(Intent(requireActivity(), Class.forName("com.razit.favorite.view.FavoriteActivity")))
        }

        binding.tvSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchMoviesFragment)
        }

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let {
                    binding.viewPager.currentItem = it
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        binding.viewPager.registerOnPageChangeCallback(object :
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