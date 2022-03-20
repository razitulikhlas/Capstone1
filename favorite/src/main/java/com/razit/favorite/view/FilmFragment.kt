package com.razit.favorite.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.razit.core.BuildConfig
import com.razit.core.adapter.MoviesAdapter
import com.razit.core.domain.model.Film
import com.razit.favorite.databinding.FragmentFavoriteFilmBinding
import com.razit.favorite.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class FilmFragment : Fragment(), MoviesAdapter.MoviesCallback {

    private var _binding: FragmentFavoriteFilmBinding? = null
    private val binding get() = _binding!!
    private val viewModelMovies: MoviesViewModel by viewModel()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteFilmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        const val CATEGORY = "category"

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

    private fun getTvShow() {
        viewModelMovies.tvShow.observe(viewLifecycleOwner) { film ->
            moviesAdapter.setData(film)
            with(binding) {
                rcvFilm.layoutManager = LinearLayoutManager(requireActivity())
                rcvFilm.adapter = moviesAdapter
            }
        }
    }

    private fun getMovies() {
        viewModelMovies.movies.observe(viewLifecycleOwner) { film ->
            moviesAdapter.setData(film)
            with(binding) {
                rcvFilm.layoutManager = LinearLayoutManager(requireActivity())
                rcvFilm.adapter = moviesAdapter
            }
        }
    }

    override fun onClick(movies: Film) {
        val directions =
            HomeFavoriteFragmentDirections.actionHomeFavoriteFragmentToDetailFragment2(movies)
        findNavController().navigate(directions)
    }

}