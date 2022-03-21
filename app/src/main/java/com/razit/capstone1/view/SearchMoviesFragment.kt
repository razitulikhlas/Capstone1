package com.razit.capstone1.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.razit.capstone1.databinding.FragmentSearchMoviesBinding
import com.razit.capstone1.viewmodel.HomeViewModel
import com.razit.core.adapter.MoviesAdapter
import com.razit.core.data.source.Resource
import com.razit.core.domain.model.Film
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


@ObsoleteCoroutinesApi
class SearchMoviesFragment : Fragment(), MoviesAdapter.MoviesCallback {

    private val viewModelMovies: HomeViewModel by viewModel()
    private var _binding: FragmentSearchMoviesBinding? = null
    private val binding get() = _binding!!
    private  var moviesAdapter: MoviesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        moviesAdapter = MoviesAdapter()
        moviesAdapter?.setListener(this)

        binding.searchMovies.requestFocus()
        val inputManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(binding.searchMovies, InputMethodManager.SHOW_IMPLICIT)
        binding.searchMovies.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lifecycleScope.launch {
                    viewModelMovies.queryChannel.send(s.toString())
                }
            }
        })

        viewModelMovies.searchResult.observe(viewLifecycleOwner) { film ->
            film.observe(viewLifecycleOwner) { resource ->
                if (resource != null) {
                    when (resource) {
                        is Resource.Success -> {
                            resource.data?.let {
                                moviesAdapter?.setData(it)
                                binding.recyclerView.adapter = moviesAdapter
                            }
                        }
                        is Resource.Error -> {
                            moviesAdapter?.setData(null)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    override fun onClick(movies: Film) {
        val directions =
            SearchMoviesFragmentDirections.actionSearchMoviesFragmentToDetailFragment(movies)
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        _binding = null
        moviesAdapter = null
        super.onDestroyView()
    }


}