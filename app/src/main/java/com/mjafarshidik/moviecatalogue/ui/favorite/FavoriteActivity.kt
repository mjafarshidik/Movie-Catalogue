package com.mjafarshidik.moviecatalogue.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.mjafarshidik.moviecatalogue.R
import com.mjafarshidik.moviecatalogue.databinding.ActivityFavoriteBinding
import com.mjafarshidik.moviecatalogue.ui.favorite.moviesfavorite.MoviesFavoriteFragment
import com.mjafarshidik.moviecatalogue.ui.favorite.tvshowsfavorite.TVShowsFavoriteFragment


class FavoriteActivity : AppCompatActivity() {
    private lateinit var favoriteBinding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.my_favorite)
        setFavoriteViewPager()
    }

    private fun setFavoriteViewPager() {
        val fragmentList = listOf(MoviesFavoriteFragment(), TVShowsFavoriteFragment())
        val tabTitle =
            listOf(resources.getString(R.string.movie), resources.getString(R.string.tv_show))

        favoriteBinding.apply {
            vpFavorite.adapter =
                ViewPagerAdapterFavorite(fragmentList, supportFragmentManager, lifecycle)
            TabLayoutMediator(tlFavorite, vpFavorite) { tab, position ->
                tab.text = tabTitle[position]
            }.attach()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}