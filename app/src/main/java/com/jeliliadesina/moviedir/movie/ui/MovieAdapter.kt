package com.jeliliadesina.moviedir.movie.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeliliadesina.moviedir.databinding.ListItemMovieBinding
import com.jeliliadesina.moviedir.movie.data.Movie

class MovieAdapter: PagedListAdapter<Movie, MovieAdapter.ViewHolder>(MovieDiffCallback()) {
    private lateinit var recyclerView: RecyclerView

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            holder.apply {
                bind(createOnClickListener(movie.id), movie, isGridLayoutManager())
                itemView.tag = movie
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    private fun createOnClickListener(id: Int): View.OnClickListener {
        return View.OnClickListener {
//            val direction = LegoSetsFragmentDirections.actionToLegosetDetailFragment(id)
//            it.findNavController().navigate(direction)
        }
    }

    private fun isGridLayoutManager() = recyclerView.layoutManager is GridLayoutManager

    class ViewHolder(private val binding: ListItemMovieBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Movie,
                 isGridLayoutManager: Boolean) {
            binding.apply {
                clickListener = listener
                movie = item
                title.visibility = if (isGridLayoutManager) View.GONE else View.VISIBLE
                executePendingBindings()
            }
        }
    }
}

private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}