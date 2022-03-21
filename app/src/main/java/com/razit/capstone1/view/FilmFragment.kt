package com.razit.capstone1.view

import android.os.Bundle
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
    private var moviesAdapter: MoviesAdapter? = null


    companion object {
        const val CATEGORY = "category"
        fun newInstance(category: String?): FilmFragment {
            val args = Bundle()
            args.putSerializable(CATEGORY, category)
            val fragment = FilmFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        moviesAdapter?.setListener(this)

        binding.frameError.btnReload.setOnClickListener {
            if (category == BuildConfig.MOVIES) {
                getMovies()
            } else {
                getTvShow()
            }
        }
    }

    private fun getMovies() {
        viewModelMovies.getMovies()
        viewModelMovies.filmMovies.observe(viewLifecycleOwner) { film ->
            if (film != null) {
                when (film) {
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> {
                        showData()
                        film.data?.let { listFilm ->
                            moviesAdapter?.setData(listFilm)
                        }
                        with(binding) {
                            rcvFilm.layoutManager = LinearLayoutManager(requireActivity())
                            rcvFilm.adapter = moviesAdapter
                        }
                    }
                    is Resource.Error -> showError()
                }
            }
        }
    }

    private fun getTvShow() {
        viewModelMovies.getTv()
        viewModelMovies.filmTv.observe(viewLifecycleOwner) { film ->
            if (film != null) {
                when (film) {
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> {
                        showData()
                        film.data?.let { listFilm ->
                            moviesAdapter?.setData(listFilm)
                        }
                        with(binding) {
                            rcvFilm.layoutManager = LinearLayoutManager(requireActivity())
                            rcvFilm.adapter = moviesAdapter
                        }
                    }
                    is Resource.Error -> showError()
                }
            }
        }
    }

    override fun onClick(movies: Film) {
        val directions = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movies)
        findNavController().navigate(directions)
    }

    private fun showLoading() {
        with(binding) {
            shimmmers.startShimmer()
            shimmmers.visibility = View.VISIBLE
            rcvFilm.visibility = View.GONE
            frameError.root.visibility = View.GONE
        }
    }

    private fun showData() {
        with(binding) {
            shimmmers.stopShimmer()
            shimmmers.visibility = View.GONE
            rcvFilm.visibility = View.VISIBLE
            frameError.root.visibility = View.GONE
        }
    }

    private fun showError() {
        with(binding) {
            shimmmers.stopShimmer()
            shimmmers.visibility = View.GONE
            rcvFilm.visibility = View.GONE
            frameError.root.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        binding.rcvFilm.adapter = null
        _binding = null
        moviesAdapter = null
        super.onDestroyView()
    }


}