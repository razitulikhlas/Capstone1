package com.razit.favorite.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.razit.favorite.R
import com.razit.favorite.di.viewModuleFavorite
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