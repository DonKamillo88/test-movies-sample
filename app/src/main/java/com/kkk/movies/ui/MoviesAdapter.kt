package com.kkk.movies.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.kkk.movies.R
import com.kkk.movies.data.model.Movie

/**
 * @author DonKamillo on 22.08.2018.
 */
class MoviesAdapter(val movies: MutableList<Movie>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    val filteredData: MutableList<Movie> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_movies_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount() = filteredData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieViewHolder).bind(filteredData[position])
    }

    override fun getFilter(): Filter = ItemFilter()

    fun updateData(data: List<Movie>) {
        movies.clear()
        movies.addAll(data)

        filteredData.clear()
        filteredData.addAll(data)
        notifyDataSetChanged()
    }


    private inner class ItemFilter : Filter() {
        override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
            val filterString = constraint.toString().toLowerCase()
            val filteredMovies = movies.filter { it.title.toLowerCase().contains(filterString) || it.genre.toLowerCase().contains(filterString) }
            val results = Filter.FilterResults()

            results.values = filteredMovies
            results.count = filteredMovies.size

            return results
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            filteredData.clear()
            filteredData.addAll(results.values as List<Movie>)
            notifyDataSetChanged()
        }

    }
}