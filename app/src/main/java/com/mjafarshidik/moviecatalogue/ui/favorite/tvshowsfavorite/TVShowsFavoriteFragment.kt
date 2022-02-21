package com.mjafarshidik.moviecatalogue.ui.favorite.tvshowsfavorite

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
import com.mjafarshidik.moviecatalogue.data.source.local.entity.TVShowsEntity
import com.mjafarshidik.moviecatalogue.databinding.FragmentTVShowsFavoriteBinding
import com.mjafarshidik.moviecatalogue.ui.detail.DetailActivity
import com.mjafarshidik.moviecatalogue.ui.favorite.FavoriteViewModel
import com.mjafarshidik.moviecatalogue.ui.home.tvshows.TVShowsAdapter
import com.mjafarshidik.moviecatalogue.viewmodel.ViewModelFactory

class TVShowsFavoriteFragment : Fragment() {

    private lateinit var tvShowFavoriteBinding: FragmentTVShowsFavoriteBinding
    private lateinit var adapterTVShow: TVShowsAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        tvShowFavoriteBinding =
            FragmentTVShowsFavoriteBinding.inflate(layoutInflater, container, false)
        return tvShowFavoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(tvShowFavoriteBinding.rvTVShowsFavorite)
        showLoading(true)
        adapterTVShow = TVShowsAdapter()

        val tvShowsFavoriteFactory = ViewModelFactory.getInstance(requireActivity())
        favoriteViewModel = ViewModelProvider(this, tvShowsFavoriteFactory)[FavoriteViewModel::class.java]

        favoriteViewModel.getFavoriteTVShow().observe(viewLifecycleOwner, { tvFav ->
            showLoading(false)
            adapterTVShow.submitList(tvFav)
            handleData()
        })
    }

    private fun handleData() {
        tvShowFavoriteBinding.rvTVShowsFavorite.apply {
            layoutManager = GridLayoutManager(context, 1)
            setHasFixedSize(true)
            adapter = adapterTVShow
        }

        adapterTVShow.setOnItemClickCallback(object : TVShowsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TVShowsEntity) {
                val moveToDetail = Intent(activity, DetailActivity::class.java)
                moveToDetail.putExtra(DetailActivity.EXTRA_DETAIL, "TVShow")
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
                val tvEntity = adapterTVShow.getSwipedData(swipedPosition)
                tvEntity?.let { favoriteViewModel.setFavoriteTVShow(tvEntity, it.isFavorite) }
                val snackBar =
                    Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.message_ok) { _ ->
                    tvEntity?.let { favoriteViewModel.setFavoriteTVShow(tvEntity, it.isFavorite) }
                }
                snackBar.show()
            }
        }
    })

    private fun showLoading(state: Boolean) {
        tvShowFavoriteBinding.apply {
            pbTVShowsFavorite.isVisible = state
            rvTVShowsFavorite.isInvisible = state
        }
    }
}