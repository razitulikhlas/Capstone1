package com.razit.capstone1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.razit.capstone1.R
import com.razit.capstone1.databinding.FragmentDetailBinding
import com.razit.capstone1.viewmodel.HomeViewModel
import com.razit.core.BuildConfig
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModelMovies: HomeViewModel by viewModel()
    private var status = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        val film = DetailFragmentArgs.fromBundle(requireArguments()).film

        with(binding) {
            Glide.with(view)
                .load(BuildConfig.IMAGES_URL + film.imageUrl)
                .apply(
                    RequestOptions.placeholderOf(com.razit.core.R.drawable.ic_loading)
                        .error(com.razit.core.R.drawable.ic_error)
                )
                .into(imgPoster)
            film.let {
                tvGenre.text = it.release
                tvDescription.text = it.description
                tvTitle.text = it.title
                tvLanguage.text = it.language
                tvDetailRating.text = it.rating.toString()

                if (viewModelMovies.checkFilmExist(it.id!!, it.type!!)) {
                    status = true
                    binding.imgFavorite.setImageResource(R.drawable.ic_favorite_active)
                } else {
                    status = false
                    binding.imgFavorite.setImageResource(R.drawable.ic_favorite)
                }
            }

            imgFavorite.setOnClickListener {

                if (status) {
                    status = false
                    viewModelMovies.deleteToFavorite(film)
                    binding.imgFavorite.setImageResource(R.drawable.ic_favorite)
                    Toast.makeText(requireContext(), R.string.deleteSuccess, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    status = true
                    viewModelMovies.saveToFavorite(film)
                    binding.imgFavorite.setImageResource(R.drawable.ic_favorite_active)
                    Toast.makeText(requireContext(), R.string.saveSuccess, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }


}