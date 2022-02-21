package com.mjafarshidik.moviecatalogue.ui.detail

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.appbar.AppBarLayout
import com.mjafarshidik.moviecatalogue.R
import com.mjafarshidik.moviecatalogue.data.source.local.entity.MoviesEntity
import com.mjafarshidik.moviecatalogue.data.source.local.entity.TVShowsEntity
import com.mjafarshidik.moviecatalogue.databinding.ActivityDetailBinding
import com.mjafarshidik.moviecatalogue.utils.NetworkInfo.IMAGE_URL
import com.mjafarshidik.moviecatalogue.viewmodel.ViewModelFactory
import com.mjafarshidik.moviecatalogue.vo.Status
import kotlin.math.abs

class DetailActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {
    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private val percentageToShowImage = 20
    private var maxScrollSize = 0
    private var isImageHidden = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        supportActionBar?.hide()

        showLoading(true)

        detailBinding.tbDetail.setNavigationOnClickListener { onBackPressed() }
        detailBinding.abDetail.addOnOffsetChangedListener(this)

        val detailFactory = ViewModelFactory.getInstance(this)
        detailViewModel = ViewModelProvider(
            this,
            detailFactory
        )[DetailViewModel::class.java]

        val idExtra = intent.getIntExtra(EXTRA_ID, 0)
        val typeExtra = intent.getStringExtra(EXTRA_DETAIL)

        if (typeExtra == "MOVIE") {
            detailViewModel.getDetailMovies(idExtra).observe(this, { movie ->
                if (movie != null) {
                    when (movie.status) {
                        Status.LOADING -> showLoading(true)
                        Status.SUCCESS -> movie.data?.let { showData(it, null) }
                        Status.ERROR -> {
                            showLoading(false)
                            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        } else {
            detailViewModel.getDetailTvShow(idExtra).observe(this, { tv ->
                if (tv != null) {
                    when (tv.status) {
                        Status.LOADING -> showLoading(true)
                        Status.SUCCESS -> tv.data?.let { showData(null, it) }
                        Status.ERROR -> {
                            showLoading(false)
                            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
    }

    private fun insertToFavorite(movie: MoviesEntity?, tv: TVShowsEntity?) {
        if (movie != null) {
            if (movie.isFavorite) {
                Toast.makeText(this, R.string.remove_from_favorite, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.add_to_favorite, Toast.LENGTH_SHORT).show()
            }
            detailViewModel.setFavoriteMovies(movie, movie.isFavorite)
        } else {
            if (tv != null) {
                if (tv.isFavorite) {
                    Toast.makeText(this, R.string.remove_from_favorite, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, R.string.add_to_favorite, Toast.LENGTH_SHORT).show()
                }
                detailViewModel.setFavoriteTVShows(tv, tv.isFavorite)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        detailBinding.apply {
            pbDetail.isVisible = state
            abDetail.isInvisible = state
            nscDetail.isInvisible = state
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (maxScrollSize == 0) maxScrollSize = appBarLayout!!.totalScrollRange

        val currentScrollPercentage: Int = (abs(verticalOffset) * 100 / maxScrollSize)

        if (currentScrollPercentage >= percentageToShowImage) {
            if (!isImageHidden) {
                isImageHidden = true
            }
        }

        if (currentScrollPercentage < percentageToShowImage) {
            if (isImageHidden) {
                isImageHidden = false
            }
        }
    }

    private fun setColor(poster: Bitmap) {
        Palette.from(poster).generate { palette ->
            val defValue = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                resources.getColor(R.color.blue, theme)
            } else {
                TODO("VERSION.SDK_INT < M")
            }
            detailBinding.apply {
                cvDetail.setCardBackgroundColor(
                    palette?.getDarkMutedColor(defValue) ?: defValue
                )
                cToolBar.setContentScrimColor(
                    palette?.getDarkMutedColor(defValue) ?: defValue
                )
                window.statusBarColor = palette?.getDarkMutedColor(defValue) ?: defValue
            }
        }
    }

    private fun showData(movie: MoviesEntity?, tvShow: TVShowsEntity?) {
        showLoading(false)
        val title = movie?.title ?: tvShow?.title
        val date = movie?.date ?: tvShow?.date
        val state = movie?.isFavorite ?: tvShow?.isFavorite
        val score = movie?.score ?: tvShow?.score
        val poster = movie?.poster ?: tvShow?.poster
        val overview = movie?.overview ?: tvShow?.overview

        setFavoriteState(state)

        detailBinding.apply {
            cToolBar.title = title
            tvDateRelease.text = resources.getString(R.string.date_release, date)
            tvScore.text = resources.getString(R.string.score, score.toString())
            tvOverview.text = overview
            imgDetail.tag = poster
            addFavorite.setOnClickListener { insertToFavorite(movie, tvShow) }
        }
        Glide.with(this)
            .asBitmap()
            .load(IMAGE_URL + poster)
            .centerCrop()
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    detailBinding.imgDetail.setImageBitmap(resource)
                    setColor(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }

    private fun setFavoriteState(state: Boolean?) {
        if (state == true) {
            detailBinding.addFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            detailBinding.addFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_DETAIL = "extra_detail"
    }
}