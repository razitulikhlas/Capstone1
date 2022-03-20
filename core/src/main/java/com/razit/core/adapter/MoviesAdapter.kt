package com.razit.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.razit.core.BuildConfig
import com.razit.core.R
import com.razit.core.databinding.ItemFilmBinding
import com.razit.core.domain.model.Film

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private var listFilm = ArrayList<Film>()
    private lateinit var moviesCallback: MoviesCallback

    fun setData(listFilm: List<Film>?) {
        this.listFilm.clear()
        listFilm?.let {
            this.listFilm.addAll(it)
        }
    }

    fun setListener(moviesCallback: MoviesCallback){
        this.moviesCallback = moviesCallback
    }
    class ViewHolder(binding : ItemFilmBinding) :RecyclerView.ViewHolder(binding.root) {
        val bind = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = listFilm[position]

        with(holder.bind) {
            tvTitle.text = film.title
            tvDate.text = film.release
            tvRating.text = film.rating.toString()
            root.setOnClickListener {
                moviesCallback.onClick(film)
            }
            Glide.with(root.context)
                .load(BuildConfig.IMAGES_URL + film.imageUrl)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(imgPoster)
        }

    }

    override fun getItemCount(): Int = listFilm.size

    interface MoviesCallback {
        fun onClick(movies: Film)
    }
}

