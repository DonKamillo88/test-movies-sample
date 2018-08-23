package com.kkk.movies.ui.movies

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kkk.movies.R
import com.kkk.movies.data.model.Movie
import kotlinx.android.synthetic.main.fragment_movies_item.view.*

/**
 * @author DonKamillo on 22.08.2018.
 */
class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(movie: Movie) {
        itemView.movie_genre.text = movie.genre
        Glide.with(itemView.context)
                .load(movie.posterUrl)
                .apply(RequestOptions()
                        .placeholder(R.drawable.placeholder)
                )
//                .preload(200,300)
//                .thumbnail(0.5F)
                .into(itemView.movie_poster)

    }
}