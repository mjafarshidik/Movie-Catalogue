package com.mjafarshidik.moviecatalogue.ui.home.movies


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mjafarshidik.moviecatalogue.R
import com.mjafarshidik.moviecatalogue.data.source.local.entity.MoviesEntity
import com.mjafarshidik.moviecatalogue.databinding.FragmentMoviesBinding
import com.mjafarshidik.moviecatalogue.ui.detail.DetailActivity
import com.mjafarshidik.moviecatalogue.viewmodel.ViewModelFactory
import com.mjafarshidik.moviecatalogue.vo.Status

class MoviesFragment : Fragment() {

    private lateinit var fragmentMovieBinding: FragmentMoviesBinding
    private lateinit var adapterMovies: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentMovieBinding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            showLoading(true)
            adapterMovies = MoviesAdapter()
            handleData()
            val moviesFactory = ViewModelFactory.getInstance(requireActivity())
            val movieViewModel = ViewModelProvider(this, moviesFactory)[MoviesViewModel::class.java]

            movieViewModel.getMovies().observe(viewLifecycleOwner, { movie ->
                if (movie != null) {
                    when (movie.status) {
                        Status.LOADING -> showLoading(true)
                        Status.SUCCESS -> {
                            showLoading(false)
                            adapterMovies.submitList(movie.data)
                        }
                        Status.ERROR -> {
                            showLoading(false)
                            Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
    }

    private fun handleData() {
        showLoading(false)
        adapterMovies = MoviesAdapter()
        fragmentMovieBinding.rvMovies.apply {
            layoutManager = GridLayoutManager(context, 1)
            setHasFixedSize(true)
            adapter = adapterMovies
        }
        adapterMovies.setOnItemClickCallback(object : MoviesAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MoviesEntity) {
                val moveActivity = Intent(activity, DetailActivity::class.java)
                moveActivity.putExtra(DetailActivity.EXTRA_DETAIL, "MOVIE")
                moveActivity.putExtra(DetailActivity.EXTRA_ID, data.id)
                startActivity(moveActivity)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        fragmentMovieBinding.apply {
            pbMovies.isVisible = state
            rvMovies.isInvisible = state
        }
    }
}