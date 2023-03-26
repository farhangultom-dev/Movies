package com.farhandev.movieapp.ui.mainscreen.fragement.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farhandev.movieapp.databinding.PopularItemBinding
import com.farhandev.movieapp.service.network.response.DetailMovieResponse
import com.farhandev.movieapp.util.NetworkConfig

class ListMovieTopRatedAdapter(val listTopRatedMovie: ArrayList<DetailMovieResponse>,val listener : OnAdapterListener)
    : RecyclerView.Adapter<ListMovieTopRatedAdapter.ViewHolder>(){

    fun setListMovie(movie: List<DetailMovieResponse>){
        listTopRatedMovie.clear()
        listTopRatedMovie.addAll(movie)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListMovieTopRatedAdapter.ViewHolder {
        val binding = PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListMovieTopRatedAdapter.ViewHolder, position: Int) {
        holder.bind(listTopRatedMovie[position])
    }

    override fun getItemCount(): Int = listTopRatedMovie.size

    inner class ViewHolder(val binding: PopularItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(movie : DetailMovieResponse){
            with(binding){
                Glide.with(itemView.context)
                    .load("${NetworkConfig.BASE_IMAGE_URL}${movie.backdropPath}")
                    .into(binding.ivMovies)

                tvMoviesTitle.text = movie.title
                tvRating.text = movie.voteAverage.toString()
                itemView.setOnClickListener { listener.OnClick(movie) }
            }
        }
    }

    interface OnAdapterListener{
        fun OnClick(movie: DetailMovieResponse)
    }
}