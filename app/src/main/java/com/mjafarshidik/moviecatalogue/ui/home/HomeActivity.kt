package com.mjafarshidik.moviecatalogue.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.tabs.TabLayoutMediator
import com.mjafarshidik.moviecatalogue.R
import com.mjafarshidik.moviecatalogue.databinding.ActivityHomeBinding
import com.mjafarshidik.moviecatalogue.ui.favorite.FavoriteActivity
import com.mjafarshidik.moviecatalogue.ui.home.movies.MoviesFragment
import com.mjafarshidik.moviecatalogue.ui.home.tvshows.TVShowsFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var homeBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        setHomeViewPager()
    }

    private fun setHomeViewPager() {
        val fragmentList = listOf(MoviesFragment(), TVShowsFragment())
        val tabTitle =
            listOf(resources.getString(R.string.movie), resources.getString(R.string.tv_show))

        homeBinding.apply {
            vpHome.adapter = ViewPagerAdapter(fragmentList, supportFragmentManager, lifecycle)
            TabLayoutMediator(tlHome, vpHome) { tab, position ->
                tab.text = tabTitle[position]
            }.attach()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnFavorite -> {
                val favoriteIntent = Intent(this@HomeActivity, FavoriteActivity::class.java)
                startActivity(favoriteIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}