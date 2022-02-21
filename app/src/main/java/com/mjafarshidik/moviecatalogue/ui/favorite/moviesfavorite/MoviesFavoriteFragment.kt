package com.mjafarshidik.moviecatalogue.ui.favorite.moviesfavorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mjafarshidik.moviecatalogue.R
import com.mjafarshidik.moviecatalogue.data.source.local.entity.MoviesEntity
import com.mjafarshidik.moviecatalogue.databinding.FragmentMoviesFavoriteBinding
import com.mjafarshidik.moviecatalogue.ui.detail.DetailActivity
import com.mjafarshidik.moviecatalogue.ui.favorite.FavoriteViewModel
import com.mjafarshidik.moviecatalogue.ui.home.movies.MoviesAdapter
import com.mjafarshidik.moviecatalogue.viewmodel.ViewModelFactory

class MoviesFavoriteFragment : Fragment() {

    private lateinit var favoriteMovieBinding: FragmentMoviesFavoriteBinding
    private lateinit var adapterMovie: MoviesAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favoriteMovieBinding =
            FragmentMoviesFavoriteBinding.inflate(layoutInflater, container, false)
        return favoriteMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(favoriteMovieBinding.rvMoviesFavorite)
        showLoading(true)
        adapterMovie = MoviesAdapter()
        val favoriteMoviesFactory = ViewModelFactory.getInstance(requireActivity())
        favoriteViewModel = ViewModelProvider(this, favoriteMoviesFactory)[FavoriteViewModel::class.java]

        favoriteViewModel.getFavoriteMovies().observe(viewLifecycleOwner, { favMovie ->
            showLoading(false)
            adapterMovie.submitList(favMovie)
            handleData()


        })
    }

    private fun handleData() {
        favoriteMovieBinding.rvMoviesFavorite.apply {
            layoutManager = GridLayoutManager(context, 1)
            setHasFixedSize(true)
            adapter = adapterMovie
        }

        adapterMovie.setOnItemClickCallback(object : MoviesAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MoviesEntity) {
                val moveToDetail = Intent(activity, DetailActivity::class.java)
                moveToDetail.putExtra(DetailActivity.EXTRA_DETAIL, "MOVIE")
                moveToDetail.putExtra(DetailActivity.EXTRA_ID, data.id)
                startActivity(moveToDetail)
            }
        })
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.bindingAdapterPosition
                val movieEntity = adapterMovie.getSwipedData(swipedPosition)
                movieEntity?.let { favoriteViewModel.setFavoriteMovies(movieEntity, it.isFavorite) }
                val snackBar =
                    Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.message_ok) { _ ->
                    movieEntity?.let { favoriteViewModel.setFavoriteMovies(movieEntity, it.isFavorite) }
                }
                snackBar.show()
            }
        }
    })

    private fun showLoading(state: Boolean) {
        favoriteMovieBinding.apply {
            pbMoviesFavorite.isVisible = state
            rvMoviesFavorite.isInvisible = state
        }
    }
}