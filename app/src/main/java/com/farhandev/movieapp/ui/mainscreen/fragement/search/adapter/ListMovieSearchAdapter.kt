package com.farhandev.movieapp.ui.mainscreen.fragement.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farhandev.movieapp.R
import com.farhandev.movieapp.databinding.SearchItemBinding
import com.farhandev.movieapp.service.network.response.DetailMovieResponse
import com.farhandev.movieapp.util.NetworkConfig

class ListMovieSearchAdapter(val listMovie: ArrayList<DetailMovieResponse>,
                             val listener : OnAdapterListener, val listenerDetail : OnAdapterListenerToDetail)
    : RecyclerView.Adapter<ListMovieSearchAdapter.ViewHolder>(){

    fun setListMovie(movie: List<DetailMovieResponse>){
        listMovie.clear()
        listMovie.addAll(movie)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListMovieSearchAdapter.ViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListMovieSearchAdapter.ViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    override fun getItemCount(): Int = listMovie.size

    inner class ViewHolder(val binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(movie : DetailMovieResponse){
            with(binding){
                Glide.with(itemView.context)
                    .load("${NetworkConfig.BASE_IMAGE_URL}${movie.backdropPath}")
                    .into(binding.ivMovies)

                tvMoviesTitle.text = movie.title
                tvRating.text = movie.voteAverage.toString()

                ivBookmark.setOnClickListener {
                    ivBookmark.setImageResource(R.drawable.ic_bookmarked)
                    listener.OnClick(movie)
                }

                itemView.setOnClickListener {
                    listenerDetail.OnClick(movie)
                }
            }
        }
    }

    interface OnAdapterListener{
        fun OnClick(movie: DetailMovieResponse)
    }

    interface OnAdapterListenerToDetail{
        fun OnClick(movie: DetailMovieResponse)
    }
}