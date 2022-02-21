package com.mjafarshidik.moviecatalogue.ui.home.tvshows

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
import com.mjafarshidik.moviecatalogue.data.source.local.entity.TVShowsEntity
import com.mjafarshidik.moviecatalogue.databinding.FragmentTVShowsBinding
import com.mjafarshidik.moviecatalogue.ui.detail.DetailActivity
import com.mjafarshidik.moviecatalogue.viewmodel.ViewModelFactory
import com.mjafarshidik.moviecatalogue.vo.Status

class TVShowsFragment : Fragment() {

    private lateinit var fragmentTvShowBinding: FragmentTVShowsBinding
    private lateinit var adapterTVSHows: TVShowsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTvShowBinding = FragmentTVShowsBinding.inflate(layoutInflater, container, false)
        return fragmentTvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            showLoading(true)

            adapterTVSHows = TVShowsAdapter()
            handleData()

            val tvShowsFactory = ViewModelFactory.getInstance(requireActivity())
            val movieViewModel =
                ViewModelProvider(this, tvShowsFactory)[TVShowsViewModel::class.java]

            movieViewModel.getTv().observe(viewLifecycleOwner, { tv ->
                if (tv != null) {
                    when (tv.status) {
                        Status.LOADING -> showLoading(true)
                        Status.SUCCESS -> {
                            showLoading(false)
                            adapterTVSHows.submitList(tv.data)

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
        adapterTVSHows = TVShowsAdapter()
        fragmentTvShowBinding.rvTVShows.apply {
            layoutManager = GridLayoutManager(context, 1)
            setHasFixedSize(true)
            adapter = adapterTVSHows
        }

        adapterTVSHows.setOnItemClickCallback(object : TVShowsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TVShowsEntity) {
                val moveActivity = Intent(activity, DetailActivity::class.java)
                moveActivity.putExtra(DetailActivity.EXTRA_DETAIL, "TVShow")
                moveActivity.putExtra(DetailActivity.EXTRA_ID, data.id)
                startActivity(moveActivity)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        fragmentTvShowBinding.apply {
            pbTVShows.isVisible = state
            rvTVShows.isInvisible = state
        }
    }
}
