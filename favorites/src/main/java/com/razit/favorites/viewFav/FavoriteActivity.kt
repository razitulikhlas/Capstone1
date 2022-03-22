package com.razit.favorites.viewFav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.razit.favorites.R
import com.razit.favorites.diFav.viewModuleFavorite
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        loadKoinModules(viewModuleFavorite)
        supportActionBar?.hide()
        supportActionBar?.elevation = 0f
    }
}