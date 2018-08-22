package com.kkk.movies.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kkk.movies.R
import com.kkk.movies.data.model.Movie

/**
 * @author DonKamillo on 22.08.2018.
 */
class MoviesAdapter(val movies: MutableList<Movie>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_movies_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount() = movies.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieViewHolder).bind(movies[position])
    }

    fun updateData(data: List<Movie>) {
        movies.clear()
        movies.addAll(data)
        notifyDataSetChanged()
    }
}