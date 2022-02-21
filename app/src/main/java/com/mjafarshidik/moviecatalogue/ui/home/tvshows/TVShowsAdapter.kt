package com.mjafarshidik.moviecatalogue.ui.home.tvshows

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mjafarshidik.moviecatalogue.R
import com.mjafarshidik.moviecatalogue.data.source.local.entity.TVShowsEntity
import com.mjafarshidik.moviecatalogue.databinding.ItemMoviesBinding
import com.mjafarshidik.moviecatalogue.utils.NetworkInfo

class TVShowsAdapter :
    PagedListAdapter<TVShowsEntity, TVShowsAdapter.DataViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemsMoviesBinding =
            ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(itemsMoviesBinding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(data!!)
        }
    }

    class DataViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TVShowsEntity) {
            with(binding) {
                tvTitle.text = data.title
                tvRating.text = data.score
                Glide.with(itemView.context)
                    .asBitmap()
                    .load(NetworkInfo.IMAGE_URL + data.poster)
                    .transform(RoundedCorners(28))
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .centerInside()
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            ivPoster.setImageBitmap(resource)

                            Palette.from(resource).generate { palette ->
                                val defValue = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    itemView.resources.getColor(
                                        R.color.blue,
                                        itemView.context.theme
                                    )
                                } else {
                                    TODO("VERSION.SDK_INT < M")
                                }
                                cardItem.setCardBackgroundColor(
                                    palette?.getDarkMutedColor(defValue) ?: defValue
                                )
                            }
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }
                    })
            }
        }
    }

    fun getSwipedData(swipedPosition: Int): TVShowsEntity? = getItem(swipedPosition)

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TVShowsEntity)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TVShowsEntity>() {
            override fun areItemsTheSame(oldItem: TVShowsEntity, newItem: TVShowsEntity): Boolean {
                return oldItem.tvId == newItem.tvId
            }

            override fun areContentsTheSame(
                oldItem: TVShowsEntity,
                newItem: TVShowsEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}