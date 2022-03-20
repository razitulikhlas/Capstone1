package com.razit.capstone1.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.razit.capstone1.databinding.FragmentFilmBinding
import com.razit.capstone1.viewmodel.HomeViewModel
import com.razit.core.BuildConfig
import com.razit.core.adapter.MoviesAdapter
import com.razit.core.data.source.Resource
import com.razit.core.domain.model.Film
import org.koin.androidx.viewmodel.ext.android.viewModel


class FilmFragment : Fragment(), MoviesAdapter.MoviesCallback {

    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!
    private val viewModelMovies: HomeViewModel by viewModel()
    private lateinit var moviesAdapter: MoviesAdapter

    companion object {

        const val CATEGORY = "category"
        private const val TAG = "FilmFragment"

        fun newInstance(category: String): FilmFragment {
            val args = Bundle()
            args.putSerializable(CATEGORY, category)
            val fragment = FilmFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onResume() {
        super.onResume()
        createFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun createFragment() {
        val category = arguments?.getString(CATEGORY)
        if (category == BuildConfig.MOVIES) {
            getMovies()
        } else {
            getTvShow()
        }
        moviesAdapter = MoviesAdapter()
        moviesAdapter.setListener(this)
    }

    private fun getMovies() {
        viewModelMovies.filmMovies.observe(this) { film ->
            if (film != null) {
                when (film) {
                    is Resource.Loading -> Log.e(TAG, "onCreate: loading")
                    is Resource.Success -> {
//                        Log.e(TAG, "onCreate: data ${film.data?.get(0)?.type}")
                        binding.progressbar.visibility = View.GONE
                        film.data?.let { listFilm ->
                            moviesAdapter.setData(listFilm)
                        }
                        with(binding) {
                            rcvFilm.layoutManager = LinearLayoutManager(requireActivity())
                            rcvFilm.adapter = moviesAdapter
                        }
                    }
                    is Resource.Error -> {
                        Log.e(TAG, "onCreate: error message ${film.message}")
                    }
                }
            }
        }
    }

    private fun getTvShow() {
        viewModelMovies.filmTv.observe(this) { film ->
            if (film != null) {
                when (film) {
                    is Resource.Loading -> Log.e(TAG, "onCreate: loading")
                    is Resource.Success -> {
                        binding.progressbar.visibility = View.GONE
                        film.data?.let { listFilm ->
                            moviesAdapter.setData(listFilm)
                        }
                        with(binding) {
                            rcvFilm.layoutManager = LinearLayoutManager(requireActivity())
                            rcvFilm.adapter = moviesAdapter
                        }
                    }
                    is Resource.Error -> {

                        Log.e(TAG, "onCreate: error message ${film.message}")
                    }
                }
            }
        }
    }

    override fun onClick(movies: Film) {
        val directions = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movies)
        findNavController().navigate(directions)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}