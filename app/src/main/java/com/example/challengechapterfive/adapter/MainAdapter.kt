package com.example.challengechapterfive.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengechapterfive.databinding.ItemContentBinding
import com.example.challengechapterfive.fragment.HomeFragmentDirections
import com.example.challengechapterfive.model.GetAllMovieResponse
import com.example.challengechapterfive.model.Result

class MainAdapter (private val movie: List<Result>): RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemContentBinding):RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemContentBinding.inflate(inflater, null, false))
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load("https://image.tmdb.org/t/p/original/"+ movie[position].posterPath)
                .into(tvCoverFilm)
            tvTittle.text = movie[position].title
            itemContent.setOnClickListener {
                val movie = Result(
                    posterPath = movie[position].posterPath,
                    title = movie[position].title,
                    overview = movie[position].overview
                )
                it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie))
            }

        }
    }

    override fun getItemCount(): Int {
        return movie.size
    }

    interface OnClickListener {
        fun onCLickItem(data: Result)
    }
}